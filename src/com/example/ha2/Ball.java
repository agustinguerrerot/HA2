package com.example.ha2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class Ball {
	public int x; // center
	public int y; // center
	public double vx;
	public double ax;
	public double vy;
	public double ay;

	public int xMax;
	public int yMax;

	public int width = 200;
	public int height = 200;

	public Rectangle box;

	private Thread t;

	private enum Direction {
		OUTSIDE, CORNER, HORIZONTAL, VERTICAL
	}

	public void jump() {
		vy = -yMax / 70;
	}

	public Ball(int x, int y, int xMax, int yMax, Rectangle box) {
		this.x = x;
		this.y = y;
		this.xMax = xMax;
		this.yMax = yMax;
		ay = yMax / 5000.0;
		this.box = box;

		t = createThread();
		t.start();
	}

	private Thread createThread() {
		return new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					x += vx;
					vx += ax;

					y += vy;
					vy += ay;

					Direction inRect = isInRect();

					if ((x + width / 2 > xMax && vx > 0)
							|| (x - width / 2 < 0 && vx < 0)
							|| inRect == Direction.HORIZONTAL) {
						vx = -vx / 2;
						x += 4 * vx;
					}
					if ((y + 1.5 * width > yMax && vy > 0)
							|| (y - width / 2 < 0 && vy < 0)
							|| inRect == Direction.VERTICAL) {
						vy = -vy / 2;
						y += 4 * vy;
					}
					if (inRect == Direction.CORNER) {
						int closestX = 0;
						int closestY = 0;
						if (Math.abs(x - box.left) < Math.abs(x - box.right)) {
							closestX = box.left;
						} else
							closestX = box.right;
						if (Math.abs(y - box.top) < Math.abs(y - box.bottom)) {
							closestY = box.top;
						} else
							closestY = box.bottom;

						double b = Math.atan(-(y - closestY) / (x - closestX));
						double g = Math.atan(-vy / vx);
						double d = 2 * b - g;

						double V = Math.sqrt(vx * vx + vy * vy);

						vx = V * Math.cos(d);
						x += 4 * vx;
						vy = V * Math.sin(d);
						y += 4 * vy;
					}

					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}

		});
	}

	private Direction isInRect() {
		int x = Math.abs(2 * this.x - (2 * box.left + box.width()));
		int y = Math.abs(2 * this.y - (2 * box.top + box.height()));
		if (x > box.width() + width)
			return Direction.OUTSIDE;

		if (y > box.height() + height)
			return Direction.OUTSIDE;

		if (x <= box.width())
			return Direction.VERTICAL;
		if (y <= box.height())
			return Direction.HORIZONTAL;
		int cornerDistSq = (x - box.width()) * (x - box.width())
				+ (y - box.height()) * (y - box.height());
		if (cornerDistSq <= 4 * width * height)
			return Direction.CORNER;
		return Direction.OUTSIDE;
	}

	public boolean isOnBottom() {
		return y > yMax - 2 * height - 10;
	}

	public void draw(Canvas canvas, Paint p) {
		canvas.drawOval(new RectF(x - width / 2, y - height / 2, x + width / 2,
				y + height / 2), p);
	}

}
