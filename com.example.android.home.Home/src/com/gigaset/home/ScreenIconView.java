package com.gigaset.home;


import java.util.ArrayList;

import com.example.android.home.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ScreenIconView extends View {
	static final int PIC_WIDTH  = 25;
	static final int PIC_HEIGHT = 10;
	static final int TEXT_SIZE  = 20;
	
	private int width;
	private int height;
	private int pages_cnt;
	private int active_page;
	private int hOffset;
	private int vPicPos;
	private int textXStartPos;
	private int textYStartPos;	
	private Drawable d;
	private Drawable icon_active;
	private Drawable icon_inactive;	
	private Paint p = new Paint();
	private String label = new String();
	private ArrayList<Drawable> pages;// = new ArrayList<Integer>();

	



	// ************** Constructors **************************
	
	public ScreenIconView(Context context) {
		super(context);
		pages = new ArrayList<Drawable>();
	}
	

	public ScreenIconView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		pages = new ArrayList<Drawable>();
	}

	/**
	 * Constructor from XML layout
	 * @param context
	 * @param attrs
	 */
	public ScreenIconView(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.atrybutes);			// get array with attributes
		
		// Retrieve attributes from XML layout		
		label 			= (String) ta.getString(R.styleable.atrybutes_label);				// "" (none) by default	
		icon_active		= ta.getDrawable(R.styleable.atrybutes_icon_active);
		icon_inactive	= ta.getDrawable(R.styleable.atrybutes_icon_inactive);	
		pages_cnt		= (int) ta.getFloat(R.styleable.atrybutes_pages_cnt,		0);		// 0 - by default
		active_page  	= (int) ta.getFloat(R.styleable.atrybutes_default_active, 	0);		// 0 - by default
		width  			= (int) ta.getFloat(R.styleable.atrybutes_width, 			0);		// 0 - by default
		height  		= (int) ta.getFloat(R.styleable.atrybutes_height, 			0);		// 0 - by default
		
		Log.v("icon_active", 	icon_active+"");
		Log.v("icon_inactive", 	icon_inactive+"");
		
		calculateMetrics();
		

		createPagesList(active_page);		
	}
	
	
	/**
	 * Update layout changeable metrics
	 */
	private void calculateMetrics()
	{
		Rect rect = new Rect();
		
		hOffset  	 	= (this.width - PIC_WIDTH)/(this.pages_cnt - 1);
		vPicPos  	 	= this.height - PIC_HEIGHT;
		
		// calculate text metrics
		setTextParameters(TEXT_SIZE, Color.WHITE);								// need to set before measure
		
		p.getTextBounds(this.label, 0, this.label.length(), rect);
		textYStartPos	= -rect.top;				
		textXStartPos 	= (int) ((this.width/2)-(p.measureText(this.label)/2));  
	}
	
	
	/**
	 * Set text parameters:
	 * - size,
	 * - color
	 */
	private void setTextParameters(float textSize, int textColor)
	{
		p.setTextSize(textSize);
		p.setColor(textColor);
	}
	
	
	/**
	 * Set active page
	 * @param pageNbr - page number
	 */
	public void createPagesList(int pageNbr)
	{				
		if((pageNbr > pages_cnt) || (pageNbr < 1))
		{
			throw new ArrayIndexOutOfBoundsException("setActivePage: invalid page number");
		}
		
		this.pages = new ArrayList<Drawable>(this.pages_cnt);
		
		for(int i = 1; i <= this.pages_cnt; i++)
		{
			if(pageNbr == i)
			{
				this.pages.add(this.icon_active);
			}
			else
			{
				this.pages.add(icon_inactive);
			}
		}
	}
	
	/**
	 * Update view. 
	 * 
	 * Need to be call after call method which change view parameters:
	 * - createPagesList,
	 * - 
	 */
	public void update()
	{
		invalidate();
	}
	

	/**
	 * Getters for active page number
	 * @return
	 */
	public int getActivePage() {
		return this.active_page;
	}


	/**
	 * Setter for active page
	 * @param pageNbr - number of active page
	 * 
	 * After call this method need call following functions:
	 * - this.createPagesList();
	 * - this.update();
	 */
	public void setActivePage(int pageNbr) {
		
		if((pageNbr > this.pages_cnt) || (pageNbr < 1))
		{
			throw new ArrayIndexOutOfBoundsException("setActivePage: invalid page number");
		}
		
		this.active_page = pageNbr;
	}


	/**
	 * Draw view
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		for(int i = 0; i < pages.size(); i++)	
		{
			d = pages.get(i);			
						
			canvas.save();
			canvas.translate(i*hOffset, vPicPos);
			d.setBounds(0, 0, PIC_WIDTH, PIC_HEIGHT);
			d.draw(canvas);
			canvas.restore();
		}
			
		canvas.drawText(this.label , textXStartPos, textYStartPos, p);
	}
}
