package com.hugoroman.pharmacys.adapters;

import android.view.View;

public interface LongClickListener extends ClickListener {

    boolean onLongItemClick(int position, View v);
}
