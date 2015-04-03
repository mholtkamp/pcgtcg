package com.pcgtcg.util;

import com.badlogic.gdx.Gdx;

public class AnimationEvent {

    private Animated target;
    
    // Default time for animations
    public static final float DEFAULT_TIME = 0.2f;
    
    // Status constants
    public static final int STATUS_UNSET    = 0;
    public static final int STATUS_READY    = 1;
    public static final int STATUS_FINISHED = 2;
    
    // Interpolation constants
    public static final int INTERP_LINEAR = 0;
    public static final int INTERP_CUBIC  = 1;
    
    // Starting values
    private float sX;
    private float sY;
    private float sWidth;
    private float sHeight;
    
    // Current values
    private float cX;
    private float cY;
    private float cWidth;
    private float cHeight;
    
    // End Values
    private float eX;
    private float eY;
    private float eWidth;
    private float eHeight;
    
    // Time values
    private float sTime;
    private float cTime;
    private float eTime;
    
    // Interpolation type
    // 0 - linear
    // 1 - cubic
    private int interpType;
    
    // Block other animation events in queue
    // until this animation event finishes.
    private boolean blocking;
    
    // Status
    // 0 - unset
    // 1 - waiting
    // 2 - updating
    // 3 - finished
    private int status;
    
    public AnimationEvent()
    {
        sTime = 0.0f;
        cTime = 0.0f;
        
        // Default end time
        eTime = 0.35f;
        
        // default interpolation type = 0 (linear)
        interpType = 0;
    }
    
    public void setTarget(Animated target)
    {
        this.target = target;
        
        // Get start values
        sX = target.getBox().x;
        sY = target.getBox().y;
        sWidth = target.getBox().width;
        sHeight = target.getBox().height;
        sTime = 0.0f;
        
        // Set current values to start values
        cX = sX;
        cY = sY;
        cWidth = sWidth;
        cHeight = sHeight;
        cTime = sTime;
    }
    
    public Animated getTarget()
    {
        return target;
    }
    
    public void setDestination(float endX,
                               float endY,
                               float endWidth,
                               float endHeight,
                               float endTime)
    {
        eX = endX;
        eY = endY;
        eWidth = endWidth;
        eHeight = endHeight;
        eTime = endTime;
        status = STATUS_READY;
    }
    
    public void setInterp(int interp)
    {
        interpType = interp;
    }
    
    public void setBlocking(boolean block)
    {
        blocking = block;
    }
    
    public void update()
    {
        if (status == STATUS_READY)
        { 
            cTime += Gdx.graphics.getDeltaTime();
            
            // Check if target is already at destination
            if(cX      == eX     &&
               cY      == eY     &&
               cWidth  == eWidth &&
               cHeight == eHeight)
            {
                status = STATUS_FINISHED;
                return;
            }
            
            if (interpType == INTERP_LINEAR)
            {
                // Linear interpolation
                float sWeight = (eTime - cTime)/eTime;
                float eWeight = 1.0f - sWeight;
                
                cX      = sWeight*sX      + eWeight*eX;
                cY      = sWeight*sY      + eWeight*eY;
                cWidth  = sWeight*sWidth  + eWeight*eWidth;
                cHeight = sWeight*sHeight + eWeight*eHeight;
            }
            
            target.setBox((int) cX, 
                          (int) cY,
                          (int) cWidth,
                          (int) cHeight);
            
            if(cTime >= eTime)
            {
                // Set target box to end values
                target.setBox((int) eX,
                              (int) eY,
                              (int) eWidth,
                              (int) eHeight);
                status = STATUS_FINISHED;
            }
        }
    }
    
    public boolean isBlocking()
    {
        return blocking;
    }
    
    public int getStatus()
    {
        return status;
    }
}
