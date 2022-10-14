package com.a530games.jackal.levels;

import com.a530games.framework.math.Vector2;
import com.a530games.jackal.Assets;
import com.a530games.jackal.Jackal;
import com.a530games.jackal.map.Beach;
import com.a530games.jackal.map.BigPillarBottomLeft;
import com.a530games.jackal.map.BigPillarBottomRight;
import com.a530games.jackal.map.BigPillarSecondLeft;
import com.a530games.jackal.map.BigPillarSecondRight;
import com.a530games.jackal.map.BigPillarThirdLeft;
import com.a530games.jackal.map.BigPillarThirdRight;
import com.a530games.jackal.map.BigPillarTopLeft;
import com.a530games.jackal.map.BigPillarTopRight;
import com.a530games.jackal.map.BottomFullWall;
import com.a530games.jackal.map.BottomHalfWall;
import com.a530games.jackal.map.Bush;
import com.a530games.jackal.map.LeftBottomCorner;
import com.a530games.jackal.map.LeftBottomFuncCorner;
import com.a530games.jackal.map.LeftFullWall;
import com.a530games.jackal.map.LeftHalfWall;
import com.a530games.jackal.map.LeftTopCorner;
import com.a530games.jackal.map.LeftTopFuncCorner;
import com.a530games.jackal.map.Map;
import com.a530games.jackal.map.RightBottomCorner;
import com.a530games.jackal.map.RightBottomFuncCorner;
import com.a530games.jackal.map.RightFullWall;
import com.a530games.jackal.map.RightHalfWall;
import com.a530games.jackal.map.RightTopCorner;
import com.a530games.jackal.map.RightTopFuncCorner;
import com.a530games.jackal.map.Rock;
import com.a530games.jackal.map.Spown;
import com.a530games.jackal.map.TopFullWall;
import com.a530games.jackal.map.TopHalfWall;
import com.a530games.jackal.map.Tree1;
import com.a530games.jackal.map.Tree2;

public class FirstLevel implements Level
{

    @Override
    public int getMapWidthInCols() {
        return 30;
    }

    @Override
    public int getMapHeightInCols() {
        return 30;
    }

    @Override
    public Vector2 getPlayerStartPosition() {
        return new Vector2(
                5 * Jackal.BLOCK_WIDTH,
                25 * Jackal.BLOCK_HEIGHT
        );
    }

    @Override
    public void addObjectsOnMap(Map map)
    {
        map.addObjectToMap(new Rock(3, 2, Rock.MOVE_ROCK_1));

        // bottom line
        for (int col = 0; col < map.mapCols; col++) {
            map.addObjectToMap(new Beach(map.mapRows - 1, col));
        }

        // right line
        /*for (int row = 1; row < this.mapRows - 1; row++) {
            this.fields[row][this.mapCols - 1] = new RightHalfWall(row, this.mapCols - 1);
        }*/

        // this.fields[0][0] = new LeftTopCorner(0, 0); // left top corner
        //this.fields[0][this.mapCols - 1] = new RightTopCorner(0, this.mapCols - 1); // right top corner
        // this.fields[this.mapRows - 1][0] = new LeftBottomCorner(this.mapRows - 1, 0); // left bottom corner
        // this.fields[this.mapRows - 1][this.mapCols - 1] = new RightBottomCorner(this.mapRows - 1, this.mapCols - 1);  // right bottom corner


        map.addObjectToMap(new Rock(9, 3, Rock.MOVE_ROCK_3));
        map.addObjectToMap(new Rock(9, 4, Rock.MOVE_ROCK_1));

        map.addObjectToMap(new Tree1(9, 15));
        map.addObjectToMap(new Tree2(9, 19));

        map.addObjectToMap(new Rock(1, 9, Rock.MOVE_BUSH_3));
        map.addObjectToMap(new Rock(2, 9, Rock.MOVE_BUSH_4));
        map.addObjectToMap(new Rock(3, 9, Rock.MOVE_BUSH_3));
        map.addObjectToMap(new Rock(4, 9, Rock.MOVE_BUSH_4));
        map.addObjectToMap(new Rock(5, 9, Rock.MOVE_BUSH_3));
        map.addObjectToMap(new Rock(6, 9, Rock.MOVE_BUSH_4));
        map.addObjectToMap(new Rock(7, 9, Rock.MOVE_BUSH_3));
        // this.addRock(14, 9, Rock.MOVE_BUSH_1);
        // this.addRock(15, 9, Rock.MOVE_BUSH_1);
        // this.addRock(16, 9, Rock.MOVE_BUSH_1);
        // this.addRock(17, 9, Rock.MOVE_BUSH_1);
        // this.addRock(18, 9, Rock.MOVE_BUSH_1);

        //
        map.addObjectToMap(new LeftTopCorner(12, 11));
        map.addObjectToMap(new TopHalfWall(12, 12));
        map.addObjectToMap(new RightTopCorner(12, 13));

        map.addObjectToMap(new RightTopCorner(14, 14));
        map.addObjectToMap(new RightHalfWall(15, 14));
        map.addObjectToMap(new RightBottomCorner(16, 14));

        map.addObjectToMap(new LeftBottomCorner(18, 11));
        map.addObjectToMap(new BottomHalfWall(18, 12));
        map.addObjectToMap(new RightBottomCorner(18, 13));

        map.addObjectToMap(new LeftTopCorner(14, 10));
        map.addObjectToMap(new LeftHalfWall(15, 10));
        map.addObjectToMap(new LeftBottomCorner(16, 10));

        // convex corners

        map.addObjectToMap(new LeftTopFuncCorner(20, 11));
        map.addObjectToMap(new TopFullWall(20, 12));
        map.addObjectToMap(new RightTopFuncCorner(20, 13));
        map.addObjectToMap(new RightFullWall(21, 13));
        map.addObjectToMap(new RightBottomFuncCorner(22, 13));
        map.addObjectToMap(new BottomFullWall(22, 12));
        map.addObjectToMap(new LeftBottomFuncCorner(22, 11));
        map.addObjectToMap(new LeftFullWall(21, 11));

        map.addObjectToMap(new BigPillarTopLeft(13, 3));
        map.addObjectToMap(new BigPillarTopRight(13, 4));
        map.addObjectToMap(new BigPillarSecondLeft(14, 3));
        map.addObjectToMap(new BigPillarSecondRight(14, 4));
        map.addObjectToMap(new BigPillarThirdLeft(15, 3));
        map.addObjectToMap(new BigPillarThirdRight(15, 4));
        map.addObjectToMap(new BigPillarBottomLeft(16, 3));
        map.addObjectToMap(new BigPillarBottomRight(16, 4));

        map.addObjectToMap(new Bush(16, 1, Assets.bush1));
        map.addObjectToMap(new Bush(16, 2, Assets.bush2));

        map.addObjectToMap(new Spown(11, 3));
        map.addObjectToMap(new Spown(11, 4));
        map.addObjectToMap(new Spown(11, 5));
        map.addObjectToMap(new Spown(11, 6));
        map.addObjectToMap(new Spown(11, 7));
    }
}
