package org.evgem.android.drachukeugenesapp.util

import android.support.v7.widget.RecyclerView

val <VH : RecyclerView.ViewHolder>RecyclerView.Adapter<VH>.lastIndex: Int get() = this.itemCount - 1