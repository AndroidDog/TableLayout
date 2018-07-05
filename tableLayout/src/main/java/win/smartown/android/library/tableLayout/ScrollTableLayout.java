package win.smartown.android.library.tableLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;


public class ScrollTableLayout extends FrameLayout {

    private TableLayout mTableLayout;
    private GestureDetector mGestureDetector;

    public ScrollTableLayout(Context context) {
        super(context);
        init(context, null);
    }

    public ScrollTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ScrollTableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 1) {
            throw new RuntimeException("FreeScrollView must contain only one child!");
        }
    }

    private void init(Context context, AttributeSet attrs) {
        mTableLayout = new TableLayout(context, attrs);
        FrameLayout.LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(mTableLayout, layoutParams);

        mGestureDetector = new GestureDetector(context, new FreeScrollGestureListener());
    }

    public void setAdapter(TableAdapter adapter) {
        mTableLayout.setAdapter(adapter);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    private class FreeScrollGestureListener implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View view = getChildAt(0);
            if (view instanceof TableLayout) {
                ((TableLayout) view).onClick(e.getX() + getScrollX(), e.getY() + getScrollY());
            }
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            View view = getChildAt(0);
            int childHeight = view.getHeight();
            int childWidth = view.getWidth();
            int toX, toY;
            if (distanceX > 0) {
                if (childWidth > getWidth()) {
                    if (getScrollX() + getWidth() >= childWidth) {
                        toX = childWidth - getWidth();
                    } else {
                        toX = (int) (getScrollX() + distanceX);
                    }
                } else {
                    toX = 0;
                }
            } else {
                if (getScrollX() + distanceX < 0) {
                    toX = 0;
                } else {
                    toX = (int) (getScrollX() + distanceX);
                }
            }
            if (distanceY > 0) {
                if (childHeight > getHeight()) {
                    if (getScrollY() + getHeight() >= childHeight) {
                        toY = childHeight - getHeight();
                    } else {
                        toY = (int) (getScrollY() + distanceY);
                    }
                } else {
                    toY = 0;
                }
            } else {
                if (getScrollY() + distanceY < 0) {
                    toY = 0;
                } else {
                    toY = (int) (getScrollY() + distanceY);
                }
            }
            scrollTo(toX, toY);
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }

}
