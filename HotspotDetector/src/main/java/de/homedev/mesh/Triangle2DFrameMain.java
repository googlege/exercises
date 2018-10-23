package de.homedev.mesh;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.homedev.mesh.util.InputOutputUtils;

/**
 * Triangle 2D GUI Data Representation
 * 
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *
 */
public class Triangle2DFrameMain extends JFrame {

	private static final long serialVersionUID = 1L;
	private ContentPaneJPanel imagePanel;
	private JScrollPane jScrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				InputStream is = null;
				try {
					is = InputOutputUtils.getFileInputStream("mesh.txt");
					List<TriangleElement> data = InputOutputUtils.readTiangleDataFile(is);
					Triangle2DFrameMain frame = new Triangle2DFrameMain(data);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (is != null) {
						try {
							is.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Triangle2DFrameMain(List<TriangleElement> data) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1024, 728);
		this.setTitle("Triangle mesh. data 2D representation");
		imagePanel = new ContentPaneJPanel(data);
		JPanel contentPane = new JPanel(new BorderLayout());
		jScrollPane = new JScrollPane(imagePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jScrollPane.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				imagePanel.repaint();
			}
		});
		jScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				imagePanel.repaint();
			}
		});

		contentPane.add(jScrollPane, BorderLayout.CENTER);
		setContentPane(contentPane);
	}

	private static class ContentPaneJPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private final List<TriangleElement> data;

		private static final int DELTA_X = 50;
		private static final int DELTA_Y = 80;
		private static final int KF_X = 100;
		private static final int KF_Y = 100;

		public ContentPaneJPanel(List<TriangleElement> data) {
			super();
			this.data = data;
			this.setLayout(new GridLayout(0, 1));
			this.setSize(new Dimension(1100, 1100));
			this.setPreferredSize(new Dimension(1100, 1100));
		}

		/**
		 * 
		 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
		 */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setFont(new Font("Arial", Font.BOLD, 11));
			for (TriangleElement el : data) {
				g.setColor(getRandomColor());
				int xp[] = { DELTA_X + el.getNode1().getX() * KF_X, DELTA_X + el.getNode2().getX() * KF_X, DELTA_X + el.getNode3().getX() * KF_X };
				int yp[] = { 1100 - DELTA_Y - el.getNode1().getY() * KF_Y, 1100 - DELTA_Y - el.getNode2().getY() * KF_Y,
						1100 - DELTA_Y - el.getNode3().getY() * KF_Y };
				int xt = (xp[0] + xp[1] + xp[2]) / 3 - 20;
				int yt = (yp[0] + yp[1] + yp[2]) / 3;

				g.fillPolygon(xp, yp, 3);

				StringBuilder text = new StringBuilder(30);
				text.append('(').append(el.getValue()).append(')');
				g.setColor(Color.WHITE);
				g.drawString(Integer.toString(el.getId()), xt, yt);
				g.setColor(Color.BLACK);
				g.drawString(text.toString(), xt, yt + 10);

			}

		}

		private Color getRandomColor() {
			Random rn = new Random();
			return new Color(rn.nextInt(256), rn.nextInt(256), rn.nextInt(256));

		}

	}

}
