package com.andalus.abomed7at55.popularmoviesstage1;

/**
 * This interface contains the callbacks for the {@link MyBackgroundTask}
 */

public interface MyBackgroundTaskCallBacks<T,D> {

    /**
     * this method runs the a parallel thread with data type
     */
    T onBackground(D data);

    /**
     * This method gets called when the parallel thread finishes
     */
    void onTaskFinished(T result);
}
