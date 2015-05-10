package com.pcgtcg.menu;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.pcg.pcgtcg;
import com.pcgtcg.util.TextOption;
import com.pcgtcg.util.TextureOption;

public class GameSelect
{
    ArrayList<GameInfo> gameList;
    BitmapFont font;
    TextOption menuOption;
    TextOption refreshOption;
    TextureOption downOption;
    TextureOption upOption;
    Texture whiteTex;
    int gameIndex;
    
    public GameSelect()
    {
        gameList      = new ArrayList<GameInfo>();
        font          = pcgtcg.manager.get("data/eras.fnt",BitmapFont.class);
        menuOption    = new TextOption("Menu" , 610, 420);
        refreshOption = new TextOption("Refresh", 610, 370);
        downOption    = new TextureOption("data/downarrow.png", 500, 95);
        upOption      = new TextureOption("data/uparrow.png", 500, 380);
        gameIndex     = 0;
        
        upOption.setValidColor(0.8f, 0.5f, 0.5f, 1.0f);
        downOption.setValidColor(0.8f, 0.5f, 0.5f, 1.0f);
    }
    
    public void update()
    {
        if(Gdx.input.justTouched() && (pcgtcg.game == null))
        {
            Vector3 touchPos = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            pcgtcg.camera.unproject(touchPos);
            float tx = touchPos.x;
            float ty = touchPos.y;
            
            for (int i = gameIndex; i < gameIndex + 3; i++)
            {
                if (i < gameList.size()               &&
                    gameList.get(i).isTouched(tx, ty) &&
                    gameList.get(i).status == GameInfo.WAITING)
                {
                    pcgtcg.netman.sendServer("JOIN."+gameList.get(i).id);
                    pcgtcg.menu.menuState = Menu.JOIN_STATE;
                    return;
                }
            }
            
            if (refreshOption.isTouched(tx, ty))
            {
                pcgtcg.menu.menuState = Menu.LIST_STATE;
                pcgtcg.netman.clearGameList();
                pcgtcg.menu.clearGameList();
                pcgtcg.netman.sendServer("LIST");
                pcgtcg.menu.clearFade();
            }
            
            if (menuOption.isTouched(tx, ty))
            {
                pcgtcg.menu.menuState = Menu.MAIN_STATE;
                pcgtcg.netman.clearGameList();
                pcgtcg.menu.clearGameList();
                pcgtcg.menu.setFade();
            }
            
            if (downOption.isTouched(tx, ty))
            {
                if (gameIndex < gameList.size() - 1)
                {
                    gameIndex++;
                    updateGameInfoBoxes();
                }
            }
            
            if (upOption.isTouched(tx, ty))
            {
                if (gameIndex > 0)
                {
                    gameIndex--;
                    updateGameInfoBoxes();
                }
            }
        }
    }
    
    public void render(SpriteBatch batch)
    {
        if (gameList.size() == 0)
        {
            font.setColor(0f, 0f, 0f, 1f);
            font.setScale(1f);
            font.draw(batch, "No Open Games", 200, 250);
        }
        else
        {
            for (int i = gameIndex; i < gameIndex + 3; i++)
            {
                if (i < gameList.size())
                    gameList.get(i).render(batch);
            }
        }
        
        menuOption.render(batch);
        refreshOption.render(batch);
        downOption.render(batch);
        upOption.render(batch);
    }
    
    public void setList(ArrayList<GameInfo> newList)
    {
        gameList = newList;
    }
    
    public void updateGameInfoBoxes()
    {
        for (int i = gameIndex; i < gameIndex + 3; i++)
        {
            if (i < gameList.size())
            {
                gameList.get(i).setPosition(25, 
                                            pcgtcg.SCREEN_HEIGHT - (i-gameIndex)*130 - 130);
            }
        }
    }
}
