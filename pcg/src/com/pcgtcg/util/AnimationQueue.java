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
        // Check if an event has already been added
        // for the newEvent's target card.
        for (int i = 0; i < queue.size(); i++)
        {
            if (queue.get(i).getTarget() == newEvent.getTarget())
            {
                // Remove the old animation event, 
                // because this new animation event will
                // control the target.
                queue.remove(i);
                i--;
            }
        }
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
