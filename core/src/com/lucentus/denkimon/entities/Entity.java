package com.lucentus.denkimon.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 * Abstract Class all Game Entities (Denkimon) will inherit from
 */
public abstract class Entity {

    /*
     * Properties
     */


    /*
     * Constructors
     */
    public Entity() {

    }


    /*
     * Abstract Methods
     */
    public abstract void render();

    public abstract void onAttack();

    public abstract void onHit();

    public abstract void onMove();


    /*
     * Methods
     */

    /**
     * Load an animation by separating a sprite sheet into frames
     * @param spriteSheetFilename the file name of the sprite sheet in the assets folder
     * @param sheetCols the number of columns the sprite sheet contains
     * @param sheetRows the number of rows the sprite sheet contains
     * @return the animation at 60 frames per second
     */
    protected Animation<TextureRegion> loadAnimation(String spriteSheetFilename, int sheetCols, int sheetRows) {

        // Separate the sprite sheet into frames to create the animation
        Texture sheet = new Texture(Gdx.files.internal(spriteSheetFilename));
        TextureRegion[][] temp = TextureRegion.split(sheet,
                                            sheet.getWidth() / sheetCols,
                                            sheet.getHeight() / sheetRows);
        TextureRegion[] frames = new TextureRegion[sheetCols * sheetRows];
        int index = 0;
        for (int i = 0; i < sheetRows; i++) {
            for (int j = 0; j < sheetCols; j++) {
                frames[index++] = temp[i][j];
            }
        }

        // Use the sprite sheet to create the animation at 60 frames per second
        return new Animation<TextureRegion>((float) (sheetCols * sheetRows) / 60, frames);
    }
}
