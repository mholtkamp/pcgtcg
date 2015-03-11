package com.pcgtcg.util;

import java.util.LinkedList;

public class AnimationQueue {

    public AnimationQueue()
    {
        queue = new LinkedList<AnimationEvent>();
    }
    
    public int getSize()
    {
        return queue.size();
    }
    
    public void add(AnimationEvent newEvent)
    {
        queue.add(newEvent);
    }
    
    public boolean update()
    {
        boolean ret = false;
        // return true if anything updated
        for(int i = 0; i < queue.size(); i++)
        {
            queue.get(i).update();
            if(queue.get(i).getStatus() == AnimationEvent.STATUS_FINISHED)
            {
                queue.remove(i);
            }
            ret = true;
        }
        
        return ret;
    }
    
    private LinkedList<AnimationEvent> queue;
}
