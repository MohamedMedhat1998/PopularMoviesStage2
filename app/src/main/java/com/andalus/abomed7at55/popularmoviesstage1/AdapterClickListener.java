package com.andalus.abomed7at55.popularmoviesstage1;

/**
 * This interface handles the click action when an item in the adapter is clicked.
 */

public interface AdapterClickListener {
    /**
     * This method gets called whenever an item is clicked
     * @param itemPosition the position of the clicked item
     */
    void onItemClicked(int itemPosition);
}
