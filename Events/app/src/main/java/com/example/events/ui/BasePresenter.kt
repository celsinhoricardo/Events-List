package com.example.event.ui


/* Base interface for presenters */
interface BasePresenter<T> {
    /**
     * Binds presenter with a view.
     *
     * @param view view associated with this presenter
     */
    fun setView(view: T)
}