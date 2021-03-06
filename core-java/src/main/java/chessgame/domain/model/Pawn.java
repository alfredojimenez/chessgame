package chessgame.domain.model;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static chessgame.domain.model.PieceColor.BLACK;
import static chessgame.domain.model.PieceColor.WHITE;

/**
 * Pawn of Chess game.
 *
 * @author Prestes, E. M.
 * @see chessgame.domain.Piece
 * @since September 2016
 */
public class Pawn extends AbstractPiece {

    private final Map<PieceColor, Function<BoardPosition, BoardPosition>> map;

    /**
     * Pawn's constructor.
     *
     * @param board Board informed.
     * @param color Color informed.
     * @see BoardPosition
     * @see PieceColor
     */
    public Pawn(Board board, PieceColor color) {
        super(board, color);

        map = Map.of(
                WHITE, BoardPosition::nextRow,
                BLACK, BoardPosition::previousRow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<BoardPosition> getAvailablePositions() {
        final Set<BoardPosition> availablePositions = new LinkedHashSet<>();

        map.forEach((color, positionFunction) -> Optional.of(this)
                .filter(piece -> color.equals(piece.getColor()))
                .map(Pawn::getPosition)
                .map(positionFunction)
                .filter(this::isEmptyBoardPosition)
                .filter(availablePositions::add)
                .filter(position -> this.isInitialPosition())
                .map(positionFunction)
                .filter(this::isEmptyBoardPosition)
                .filter(availablePositions::add));

        return availablePositions;
    }
}
