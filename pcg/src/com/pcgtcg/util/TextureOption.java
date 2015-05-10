package com.pcgtcg.util;

import com.badlogic.gdx.graphics.Texture;
import com.pcg.pcgtcg;

public class TextureOption extends Option
{

    public TextureOption(float x,
                         float y)
    {
        super(x,y);
        setWidth(64.0f);
        setHeight(64.0f);
    }
    
    public TextureOption(String path,
                         float x,
                         float y)
    {
        this(x,y);
        texture = pcgtcg.manager.get(path, Texture.class);
    }
    
    public TextureOption(Texture texture,
                         float x,
                         float y)
    {
        this(x,y);
        this.texture = texture;
    }
}
