package com.pcgtcg.util;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class HistoryQueue {
    
    public final float DEFAULT_HISTORY_X = 665.0f;
    public final float DEFAULT_HISTORY_Y = 260.0f;
    
    private LinkedList<HistoryToast> queue;
    
    public HistoryQueue()
    {
        queue = new LinkedList<HistoryToast>();
    }
    
    public void add(HistoryToast toast)
    {
        queue.addFirst(toast);
    }
    
    public void update()
    {
        for (int i = 0; i < queue.size(); i++)
        {
            queue.get(i).update();
            if (queue.get(i).getTime() >= HistoryToast.maxTime)
            {
                queue.remove(i);
                i--;
            }
        }
        
        updatePositions();
    }
    
    public void render(SpriteBatch batch)
    {
        // Render all the toasts!
        for (int i = 0; i < queue.size(); i++)
        {
            queue.get(i).render(batch);
        }
    }
    
    private void updatePositions()
    {
        for(int i = 0; i < queue.size(); i++)
        {
            Rectangle box = queue.get(i).box;
            box.x = DEFAULT_HISTORY_X;
            box.y = DEFAULT_HISTORY_Y + i*HistoryToast.DEFAULT_TOAST_HEIGHT;
        }
    }

}
