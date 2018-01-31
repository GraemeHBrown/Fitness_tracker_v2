package com.codeclan.example.fitnesstrackerapp.activity;

import com.codeclan.example.fitnesstrackerapp.R;

/**
 * Created by graemebrown on 31/01/2018.
 */

 public class ActivityImages {

    private static Integer[] imgid = {R.drawable.road_bike, R.drawable.mtn_bike, R.drawable.running, R.drawable.swimming

    };

    public static int getImagesResourceIdForActivityType(String activityType) {
        int resourceId = 0;
        if (activityType.equals("Road Biking")) {
            resourceId = imgid[0];
        } else if (activityType.equals("Mtn Biking")) {
            resourceId = imgid[1];
        } else if (activityType.equals("Road Running")) {
            resourceId = imgid[2];
        } else if (activityType.equals("Swimming")) {
            resourceId = imgid[3];
        }

        return resourceId;
    }
}
