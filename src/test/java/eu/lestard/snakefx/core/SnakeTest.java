package eu.lestard.snakefx.core;

import eu.lestard.grid.Cell;
import eu.lestard.grid.GridModel;
import eu.lestard.snakefx.viewmodel.CentralViewModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


@SuppressWarnings("unchecked")
public class SnakeTest {
    private Snake snake;

    private GridModel<State> gridModel;

    private GameLoop gameLoopMock;

    private static final int X = 4;
    private static final int Y = 2;

    private CentralViewModel viewModel;

    @Before
    public void setUp() {
        gridModel = new GridModel<>();
        gridModel.setNumberOfColumns(10);
        gridModel.setNumberOfRows(10);
        gridModel.getCells().forEach(cell -> cell.changeState(State.EMPTY));

        gameLoopMock = mock(GameLoop.class);

        viewModel = new CentralViewModel();

        snake = new Snake(viewModel, gridModel, gameLoopMock);
        Whitebox.setInternalState(snake, "x", X);
        Whitebox.setInternalState(snake, "y", Y);

    }

    @Test
    public void testChangeDirection() {
        viewModel.snakeDirection.set(Direction.LEFT);

        assertThat(snake.nextDirection).isEqualTo(Direction.LEFT);
    }

    /**
     * When the new direction has the same orientation as the old one ( both are
     * horizontal or both are vertical) no change of the direction should be
     * made.
     *
     * Otherwise the head of the snake would move directly into the tail.
     *
     *
     * But if the player pressed LEFT and then DOWN faster then the gap between
     * two frames, then the Snake would make a 180 degree turnaround. The LEFT
     * keypress wouldn't be filtered out because LEFT has another orientation
     * then UP and the DOWN keypress wouldn't be filtered out because LEFT
     * (which is the "next direction" now) has another orientation then DOWN.
     *
     * To prevent this we have two variables for the direction: "nextDirection"
     * and "currentDirection". When the player likes to change the direction,
     * only nextDirection is changed but he test whether the orientation is the
     * same is done with the "currentDirection". When the snake moves, the
     * "currentDirection" variable gets the value from "nextDirection".
     */
    @Test
    public void testChangeDirectionNewHasSameOrientationAsOld() {
        // Snake is initialized with currentDirection=UP and nextDirection=UP
        snake.init();

        viewModel.snakeDirection.set(Direction.DOWN);

        // currentDirection and nextDirection is still UP because the
        // orientation is the same
        assertThat(snake.nextDirection).isEqualTo(Direction.UP);
        assertThat(snake.currentDirection).isEqualTo(Direction.UP);

        viewModel.snakeDirection.set(Direction.LEFT);
        // the nextDirection is now changed...
        assertThat(snake.nextDirection).isEqualTo(Direction.LEFT);
        // ... the currentDirection is still the old one. It is only changed
        // when the snake moves.
        assertThat(snake.currentDirection).isEqualTo(Direction.UP);

        viewModel.snakeDirection.set(Direction.DOWN);
        // nextDirection is not changed as the currentDirection is still UP and
        // has the same orientation as DOWN
        assertThat(snake.nextDirection).isEqualTo(Direction.LEFT);
        assertThat(snake.currentDirection).isEqualTo(Direction.UP);

        snake.move();

        assertThat(snake.nextDirection).isEqualTo(Direction.LEFT);
        // now the currentDirection has changed.
        assertThat(snake.currentDirection).isEqualTo(Direction.LEFT);


        viewModel.snakeDirection.set(Direction.DOWN);

        assertThat(snake.nextDirection).isEqualTo(Direction.DOWN);

        // this direction is not valid because the snake haven't moved yet. RIGHT would mean a 180 degree turn-around.
        viewModel.snakeDirection.set(Direction.RIGHT);

        // the next direction has to be still DOWN as this was the last valid direction change.
        assertThat(snake.nextDirection).isEqualTo(Direction.DOWN);

        // this is also valid. When the snake haven't moved yet you can change the direction as often as you like as long as the new direction is valid.
        viewModel.snakeDirection.set(Direction.UP);

        assertThat(snake.nextDirection).isEqualTo(Direction.UP);

    }

    @Test
    public void testMove() {

        snake.init();

        snake.move();

        assertThat(snake.head).isEqualTo(gridModel.getCell(X, Y - 1));

        assertThat(gridModel.getCell(X, Y).getState()).isEqualTo(State.EMPTY);
    }

    /**
     * When the snake moves to a field that has the state "FOOD" the snake
     * should grow by 1 field.
     */
    @Test
    public void testGrow() {
        // at the start field1 is the head
        final Cell<State> field1 = gridModel.getCell(X, Y);

        // field2 is above field1
        final Cell<State> field2 = gridModel.getCell(X, Y - 1);
        field2.changeState(State.FOOD);
        // field3 is above field2
        final Cell<State> field3 = gridModel.getCell(X, Y - 2);
        assertThat(field3.getState()).isEqualTo(State.EMPTY);

        // field4 is on the right of field3
        final Cell<State> field4 = gridModel.getCell(X+1, Y -2);
        assertThat(field4.getState()).isEqualTo(State.EMPTY);


        snake.init();

        snake.move();

        // the head of the snake is now on field2
        assertThat(snake.head).isEqualTo(field2);

        // field1 is now a part of the tail
        assertThat(field1.getState()).isEqualTo(State.TAIL);
        assertThat(snake.tail).containsOnly(field1);

        // One Point has to be added.
        assertThat(viewModel.points.get()).isEqualTo(1);


        // in our test the new food is generated on field4
        field4.changeState(State.FOOD);


        // Now the snake is moving another field forward. This time the new
        // field (field3)
        // is empty.
        snake.move();

        // field3 becomes the new head
        assertThat(snake.head).isEqualTo(field3);

        // field2 becomes part of the tail
        assertThat(field2.getState()).isEqualTo(State.TAIL);
        assertThat(snake.tail).containsOnly(field2);


        // field1 is now empty
        assertThat(field1.getState()).isEqualTo(State.EMPTY);

        // change the direction to right so that we land on field4
        viewModel.snakeDirection.set(Direction.RIGHT);

        snake.move();

        assertThat(snake.head).isEqualTo(field4);

        assertThat(field4.getState()).isEqualTo(State.HEAD);
        assertThat(field3.getState()).isEqualTo(State.TAIL);
        assertThat(field2.getState()).isEqualTo(State.TAIL);
        assertThat(field1.getState()).isEqualTo(State.EMPTY);

        assertThat(snake.tail).containsOnly(field3, field2);


    }

    @Test
    public void testCollision() {

        snake.init();

        final Cell<State> tail = gridModel.getCell(X, Y - 1);
        tail.changeState(State.TAIL);

        snake.move();

        assertThat(viewModel.collision.get()).isTrue();
    }

    /**
     * When the newGame method is called, the head must be set to null and the
     * tails arraylist has to be reset.
     *
     * The init method has to be called too.
     */
    @Test
    public void testNewGame() {

        Cell<State> head = gridModel.getCell(X, Y);
        Cell<State> food = gridModel.getCell(X, Y - 1);
        food.changeState(State.FOOD);

        snake.init();
        snake.move();

        assertThat(snake.head).isEqualTo(food);
        assertThat(snake.tail).hasSize(1);
        assertThat(snake.tail).contains(head);

        snake.newGame();

        // the head is reset and the tail is empty
        assertThat(snake.head).isEqualTo(head);
        assertThat(snake.tail).isEmpty();

    }

    /**
     * From a given field get the field next to it from a given direction.
     */
    @Test
    public void testGetFromDirection() {
        gridModel.setNumberOfColumns(4);
        gridModel.setNumberOfRows(4);
        final Cell<State> x2y2 = gridModel.getCell(2, 2);

        final Cell<State> x2y3 = snake.getFromDirection(x2y2, Direction.DOWN);

        assertThat(x2y3.getColumn()).isEqualTo(2);
        assertThat(x2y3.getRow()).isEqualTo(3);

        final Cell<State> x3y3 = snake.getFromDirection(x2y3, Direction.RIGHT);

        assertThat(x3y3.getColumn()).isEqualTo(3);
        assertThat(x3y3.getRow()).isEqualTo(3);
    }

    /**
     * In the game when the snake moves outside of the grid on one side it
     * appears again on the other side.
     *
     * When a field is located directly at the border of the grid and the
     * getFromDirection method is called with the direction to the outside of
     * the grid, the field on the other side of the grid on the same row/column
     * has to be returned.
     */
    @Test
    public void testGetFromDirectionOtherSideOfTheGrid() {
        gridModel.setNumberOfColumns(4);
        gridModel.setNumberOfRows(4);

        Cell<State> x0y3 = gridModel.getCell(0, 3);
        final Cell<State> x3y3 = snake.getFromDirection(x0y3, Direction.LEFT);

        assertThat(x3y3.getColumn()).isEqualTo(3);
        assertThat(x3y3.getRow()).isEqualTo(3);

        x0y3 = snake.getFromDirection(x3y3, Direction.RIGHT);
        assertThat(x0y3.getColumn()).isEqualTo(0);
        assertThat(x0y3.getRow()).isEqualTo(3);

        Cell<State> x2y0 = gridModel.getCell(2, 0);
        final Cell<State> x2y3 = snake.getFromDirection(x2y0, Direction.UP);

        assertThat(x2y3.getColumn()).isEqualTo(2);
        assertThat(x2y3.getRow()).isEqualTo(3);

        x2y0 = snake.getFromDirection(x2y3, Direction.DOWN);
        assertThat(x2y0.getColumn()).isEqualTo(2);
        assertThat(x2y0.getRow()).isEqualTo(0);
    }
}
