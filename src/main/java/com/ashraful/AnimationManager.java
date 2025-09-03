package com.ashraful;

import java.util.ArrayList;
import java.util.List;

class AnimationManager {
    public List<Keyframe> keyframes;

    AnimationManager() {
        keyframes = new ArrayList<Keyframe>();
    }

    public void addKeyFrame(Keyframe keyFrame) {
        keyframes.add(keyFrame);
    }

    public int getProperty() {
        int time = Config.getInstance().time;

        Keyframe startKeyFrame = this.keyframes.get(0);
        Keyframe endKeyframe = this.keyframes.get(this.keyframes.size() - 1);

        int X = 0;
        if (time < startKeyFrame.time) {
            X = startKeyFrame.x;
        }

        if (time > endKeyframe.time) {
            X = endKeyframe.x;
        }

        for (int i = 0; i < this.keyframes.size() - 1; i++) {
            Keyframe leftKey = this.keyframes.get(i);
            Keyframe rightKey = this.keyframes.get(i + 1);

            if (time >= leftKey.time && time <= rightKey.time) {
                double leftTime = leftKey.time;
                double rightTime = rightKey.time;

                double stX = leftKey.x
                        + (((double) time - leftTime) / (rightTime - leftTime)) * (rightKey.x - leftKey.x);
                X = (int) stX;
            }
        }

        return X;
    }
}