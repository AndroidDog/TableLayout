package win.smartown.android.library.tableLayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * Created by Smartown on 2017/7/19.
 */
public class TableLayout extends LinearLayout implements TableColumn.Callback {

    private int tableMode;
    private int tableRowHeight;
    private int tableDividerSize;
    private int tableDividerColor;
    private int tableColumnPadding;
    private int tableTextGravity;
    private int tableTextSize;
    private int tableTextColor;

    private Paint paint;

    public TableLayout(Context context) {
        super(context);
        init(null);
    }

    public TableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public TableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TableLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        Log.i("TableLayout", "init");
        setOrientation(HORIZONTAL);
        setWillNotDraw(false);
        paint = new Paint();
        paint.setAntiAlias(true);

        if (attrs != null) {
            TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.TableLayout);
            tableMode = typedArray.getInt(R.styleable.TableLayout_tableMode, 0);
            tableRowHeight = typedArray.getDimensionPixelSize(R.styleable.TableLayout_tableRowHeight, (int) Util.dip2px(getResources(), 36));
            tableDividerSize = typedArray.getDimensionPixelSize(R.styleable.TableLayout_tableDividerSize, 1);
            tableDividerColor = typedArray.getColor(R.styleable.TableLayout_tableDividerColor, Color.GRAY);
            tableColumnPadding = typedArray.getDimensionPixelSize(R.styleable.TableLayout_tableColumnPadding, 0);
            tableTextGravity = typedArray.getInt(R.styleable.TableLayout_tableTextGravity, 0);
            tableTextSize = typedArray.getDimensionPixelSize(R.styleable.TableLayout_tableTextSize, (int) Util.dip2px(getResources(), 12));
            tableTextColor = typedArray.getColor(R.styleable.TableLayout_tableTextColor, Color.GRAY);
            typedArray.recycle();
        } else {
            tableMode = 0;
            tableRowHeight = (int) Util.dip2px(getResources(), 36);
            tableDividerSize = 1;
            tableDividerColor = Color.GRAY;
            tableColumnPadding = 0;
            tableTextGravity = 0;
            tableTextSize = (int) Util.dip2px(getResources(), 12);
            tableTextColor = Color.GRAY;
        }
        if (isInEditMode()) {
            addView(new TableColumn(getContext(), this));
            addView(new TableColumn(getContext(), this));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(tableDividerColor);
        int drawnWidth = 0;
        int maxRowCount = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            TableColumn column = (TableColumn) getChildAt(i);
            maxRowCount = Math.max(maxRowCount, column.getChildCount());
            if (i > 0) {
                if (tableDividerSize > 1) {
                    canvas.drawRect(drawnWidth - tableDividerSize / 2, 0, drawnWidth + tableDividerSize / 2, getHeight(), paint);
                } else {
                    canvas.drawRect(drawnWidth - tableDividerSize, 0, drawnWidth, getHeight(), paint);
                }
            }
            drawnWidth += column.getWidth();
        }
        for (int i = 1; i < maxRowCount; i++) {
            float y = i * tableRowHeight;
            if (tableDividerSize > 1) {
                canvas.drawRect(0, y - tableDividerSize / 2, getWidth(), y + tableDividerSize / 2, paint);
            } else {
                canvas.drawRect(0, y - tableDividerSize, getWidth(), y, paint);
            }
        }
    }

    @Override
    public TableLayout getTableLayout() {
        return this;
    }

    public int getTableMode() {
        return tableMode;
    }

    public int getTableRowHeight() {
        return tableRowHeight;
    }

    public int getTableDividerSize() {
        return tableDividerSize;
    }

    public int getTableDividerColor() {
        return tableDividerColor;
    }

    public int getTableColumnPadding() {
        return tableColumnPadding;
    }

    public int getTableTextGravity() {
        return tableTextGravity;
    }

    public int getTableTextSize() {
        return tableTextSize;
    }

    public int getTableTextColor() {
        return tableTextColor;
    }

}