package me.jpinz.wave.widgets;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * A custom {@link ImageView} that improves the performance by not passing
 * requestLayout() to its parent, taking advantage of knowing that image size
 * won't change once set.
 */
public class LayoutSuppressingImageView extends ImageView {

    /**
     * @param context The {@link Context} to use
     * @param attrs   The attributes of the XML tag that is inflating the view
     */
    public LayoutSuppressingImageView(final Context context, final AttributeSet attrs) {
        super(context, attrs);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void requestLayout() {
        forceLayout();
    }

}