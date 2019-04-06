package dyskretna;

import com.sun.org.apache.xpath.internal.operations.Equals;
import javafx.util.Pair;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Bartosz Wesolowski on 08.04.2017.
 */
public class Vertex {

    private int id;

    private Pair<Double, Double> coordinates;

    public Vertex(final int id, final Pair<Double, Double> coordinates) {
        this.id = id;
        this.coordinates = coordinates;
    }

    public int getId() {
        return id;
    }

    public Pair<Double, Double> getCoordinates() {
        return coordinates;
    }

    public double distance(final Vertex other) {
        final Pair<Double, Double> otherCoords = other.getCoordinates();
        return Math.hypot(coordinates.getKey() - otherCoords.getKey(),
                coordinates.getValue() - otherCoords.getValue());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("Id", id)
            .append("Coordinates", coordinates)
            .toString();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || !(obj instanceof Vertex)) {
            return false;
        }
        final Vertex other = (Vertex) obj;
        return new EqualsBuilder()
                .append(this.id, other.getId())
                .append(this.coordinates, other.getCoordinates())
                .build();
    }
}
