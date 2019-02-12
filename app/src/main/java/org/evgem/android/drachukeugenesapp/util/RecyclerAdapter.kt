package org.evgem.android.drachukeugenesapp.util

import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder

val <VH : ViewHolder>Adapter<VH>.lastIndex: Int get() = this.itemCount - 1