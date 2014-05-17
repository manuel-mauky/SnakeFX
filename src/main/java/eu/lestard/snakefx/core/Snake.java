package eu.lestard.snakefx.core;

import eu.lestard.snakefx.viewmodel.CentralViewModel;

import java.util.ArrayList;
import java.util.List;

import static eu.lestard.snakefx.config.Config.*;

/**
 * This class represents the snake.
 *
 * @author manuel.mauky
 */
public class Snake {

    private Field head;

    private final Grid grid;

    private final int x;
    private final int y;

    private Direction currentDirection;

    private Direction nextDirection;

    private final List<Field> tail;


    private final CentralViewModel viewModel;


    /**
     * @param viewModel the viewModel
     * @param grid      the grid on which the snake is created
     * @param gameLoop  the gameloop that is used for the movement of the snake
     */
    public Snake(final CentralViewModel viewModel, final Grid grid, final GameLoop gameLoop) {
        this.viewModel = viewModel;
        this.grid = grid;
        x = SNAKE_START_X.get();
        y = SNAKE_START_Y.get();

        tail = new ArrayList<Field>();

        gameLoop.addActions(x -> {
            move();
        });

        viewModel.snakeDirection.addListener( (observable,oldDirection,newDirection) -> {
            Snake.this.changeDirection(newDirection);
        });
    }

    /**
     * Initalizes the fields of the snake.
     */
    public void init() {
        setHead(grid.getXY(x, y));

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
        if (!newDirection.hasSameOrientation(currentDirection)) {
            nextDirection = newDirection;
        }
    }

    /**
     * Move the snake by one field.
     */
    void move() {
        currentDirection = nextDirection;

        final Field newHead = grid.getFromDirection(head, currentDirection);

        if (newHead.getState().equals(State.TAIL)) {
            viewModel.collision.set(true);
            return;
        }

        boolean grow = false;
        if (newHead.getState().equals(State.FOOD)) {
            grow = true;
        }

        Field lastField = head;

        for (int i = 0; i < tail.size(); i++) {
            final Field f = tail.get(i);

            lastField.changeState(State.TAIL);
            tail.set(i, lastField);

            lastField = f;
        }

        if (grow) {
            grow(lastField);
            addPoints();
        } else {
            lastField.changeState(State.EMPTY);
        }

        setHead(newHead);
    }

    public void newGame() {
        tail.clear();
        init();
    }

    private void setHead(final Field head) {
        this.head = head;
        head.changeState(State.HEAD);
    }

    /**
     * The given field is added to the tail of the snake and gets the state TAIL.
     *
     * @param field
     */
    private void grow(final Field field) {
        field.changeState(State.TAIL);
        tail.add(field);
    }

    private void addPoints() {
        final int current = viewModel.points.get();
        viewModel.points.set(current + 1);
    }


}