package eu.lestard.snakefx.core;

import eu.lestard.grid.Cell;
import eu.lestard.grid.GridModel;
import eu.lestard.snakefx.viewmodel.CentralViewModel;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

import static eu.lestard.snakefx.config.Config.*;

/**
 * This class represents the snake.
 *
 * @author manuel.mauky
 */
@Singleton
public class Snake {


    private final int x;
    private final int y;

    Direction currentDirection;

    Direction nextDirection;

    Cell<State> head;
    final List<Cell<State>> tail;


    private final CentralViewModel viewModel;
    private final GridModel<State> gridModel;

    /**
     * @param viewModel the viewModel
     * @param gridModel      the grid on which the snake is created
     * @param gameLoop  the gameloop that is used for the movement of the snake
     */
    public Snake(final CentralViewModel viewModel, final GridModel<State> gridModel, final GameLoop gameLoop) {
        this.viewModel = viewModel;
        this.gridModel = gridModel;
        x = SNAKE_START_X.get();
        y = SNAKE_START_Y.get();

        tail = new ArrayList<>();

        gameLoop.addAction(this::move);

        viewModel.snakeDirection.addListener((observable,oldDirection,newDirection) ->
                Snake.this.changeDirection(newDirection));
    }

    /**
     * Initalizes the fields of the snake.
     */
    public void init() {
        setHead(gridModel.getCell(x, y));

        viewModel.collision.set(false);

        viewModel.points.set(0);

        currentDirection = Direction.UP;
        nextDirection = Direction.UP;
    }

    /**
     * Change the direction of the snake. The direction is only changed when the new direction has <bold>not</bold> the
     * same orientation as the old one.
     * <p/>
     * For example, when the snake currently has the direction UP and the new direction should be DOWN, nothing will
     * happend because both directions are vertical.
     * <p/>
     * This is to prevent the snake from moving directly into its own tail.
     *
     * @param newDirection
     */
    private void changeDirection(final Direction newDirection) {
        if (newDirection.hasSameOrientation(currentDirection)) {
            viewModel.snakeDirection.setValue(nextDirection);
        } else {
            nextDirection = newDirection;
        }
    }

    /**
     * Move the snake by one field.
     */
    void move() {
        currentDirection = nextDirection;

        final Cell<State> newHead = getFromDirection(head, currentDirection);

        if (newHead.getState().equals(State.TAIL)) {
            viewModel.collision.set(true);
            return;
        }

        boolean snakeWillGrow = false;
        if (newHead.getState().equals(State.FOOD)) {
            snakeWillGrow = true;
        }

        Cell<State> lastField = head;

        for (int i = 0; i < tail.size(); i++) {
            final Cell<State>  f = tail.get(i);

            lastField.changeState(State.TAIL);
            tail.set(i, lastField);

            lastField = f;
        }

        if (snakeWillGrow) {
            grow(lastField);
            addPoints();
        } else {
            lastField.changeState(State.EMPTY);
        }

        setHead(newHead);
    }

    /**
     * returns the cell that is located next to the given cell in the given
     * direction.
     *
     * @param cell
     * @param direction
     * @return the cell in the given direction
     */
    Cell<State> getFromDirection(Cell<State> cell, Direction direction) {
        int column = cell.getColumn();
        int row = cell.getRow();

        switch(direction){
        case UP:
            row -= 1;
            break;
        case DOWN:
            row += 1;
            break;
        case LEFT:
            column -= 1;
            break;
        case RIGHT:
            column += 1;
            break;
        }

        column += gridModel.getNumberOfColumns();
        column = column % gridModel.getNumberOfColumns();

        row += gridModel.getNumberOfRows();
        row = row % gridModel.getNumberOfRows();

        return gridModel.getCell(column, row);
    }

    public void newGame() {
        tail.clear();
        init();
    }

    private void setHead(final Cell<State> head) {
        this.head = head;
        head.changeState(State.HEAD);
    }

    /**
     * The given field is added to the tail of the snake and gets the state TAIL.
     *
     * @param field
     */
    private void grow(final Cell<State> field) {
        field.changeState(State.TAIL);
        tail.add(field);
    }

    private void addPoints() {
        final int current = viewModel.points.get();
        viewModel.points.set(current + 1);
    }


}