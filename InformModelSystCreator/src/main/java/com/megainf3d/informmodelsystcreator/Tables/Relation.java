package com.megainf3d.informmodelsystcreator.Tables;

import com.megainf3d.informmodelsystcreator.Adapters.TableAdapter;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "relation")
@XmlAccessorType(XmlAccessType.FIELD)
public class Relation extends Group {
    @XmlJavaTypeAdapter( TableAdapter.class )
    protected Table First;
    @XmlJavaTypeAdapter( TableAdapter.class )
    protected Table Last;
    @XmlElement(required = true)
    protected RelationType Type;
    public Relation(){}
    public Relation(Table first, Table last, RelationType type)
    {
        super(new Line(Relation.getPointsOfConnection(first, last).item1.getX(),
                Relation.getPointsOfConnection(first, last).item1.getY(),
                Relation.getPointsOfConnection(first, last).item2.getX(),
                Relation.getPointsOfConnection(first, last).item2.getY()),
                new Text(getTextX(Relation.getPointsOfConnection(first, last).item1.getX(),first),getTextY(Relation.getPointsOfConnection(first, last).item1.getY(),first),getType(type,true)),
                new Text(getTextX(Relation.getPointsOfConnection(first, last).item2.getX(),last),getTextY(Relation.getPointsOfConnection(first, last).item2.getY(),last),getType(type,false)));
        First = first;
        Last = last;
        Type = type;
    }

    private static String getType(RelationType type,boolean first)
    {
        switch (type)
        {
            case MANY_TO_MANY: {
                return "*";
            }
            case ONE_TO_ONE: {
                return "1";
            }
            case ONE_TO_MANY: {
                if(first) return "1";
                else return "*";
            }
        }
        return "?";
    }
    private static double getTextX(double x,Table block)
    {
        if(x>=block.X && x<(block.X+block.Width))
            return x-10;
        else return x+5;
    }
    private static double getTextY(double y,Table block)
    {
        if(y>=block.Y && y<(block.Y+block.Height))
            return y-4;
        else return y+11;
    }

    public Table getFirst() {
        return First;
    }

    public Table getLast() {
        return Last;
    }

    public RelationType getType() {
        return Type;
    }



    /**
     * @param first table
     * @param last table
     * @return array of points
     */
    public static ConnectPoint<Point2D, Point2D> getPointsOfConnection(Table first, Table last) {
        var fromPoints = first.getArrayOfMinMaxPoints();
        var toPoints = last.getArrayOfMinMaxPoints();
        Point2D pointFromFinal = Point2D.ZERO;
        Point2D pointToFinal = Point2D.ZERO;
        double lowestDistance = Double.POSITIVE_INFINITY;
        for (Point2D fromPoint : fromPoints) {
            for (Point2D toPoint : toPoints) {
                var newDistance = fromPoint.distance(toPoint);
                if (newDistance < lowestDistance) {
                    pointFromFinal = fromPoint;
                    pointToFinal = toPoint;
                    lowestDistance = newDistance;
                }
            }
        }
        return new ConnectPoint<>(pointFromFinal, pointToFinal);
    }
}
