package sample;

import java.awt.Color;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class Ball2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame();
		frame.setTitle("Ball Animation");
		frame.setSize( 640, 480 );
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MyPanel2_4 panel = new MyPanel2_4(frame.getRootPane().getWidth(),frame.getRootPane().getHeight());
		frame.getContentPane().add(panel);
		frame.setVisible(true);
		
		class frame{
			private double width ;
			private double height ;
			
			public frame(){
				this.height = frame.getRootPane().getHeight(); 
				this.width = frame.getRootPane().getWidth(); 
			}
		}
	}
}

class MyPanel2_4 extends JPanel implements Runnable,MouseMotionListener{
	private static final long serialVersionUID = 1L;
	
	Ball2_4 ball;
	Ball2_4 ball2;
	Mallet2_4 mallet;
	Ball2 frame ;
	
	public MyPanel2_4( int width, int height ) {
		setBackground( Color.white );
		addMouseMotionListener( this );
		
		ball = new Ball2_4(100,100,0.5,1,10);
		ball2 = new Ball2_4(200,200,0,1,10);
		mallet = new Mallet2_4(0,0,20);
		
		Thread refresh = new Thread( this );
		refresh.start();
	}
	
	public void paintComponent( Graphics g ){
		super.paintComponent( g );
		ball.forward();
		ball2.forward();
		
		double dis,v,cos,nx,ny,tx,ty,vx,vy,nnx,nny,tty,ttx,ccos,ddis;
		double dis2,v2,cos2,nx2,ny2,tx2,ty2,vx2,vy2;
		
		g.setColor( Color.red );
		g.fillOval( (int)(ball.getX() - ball.getRadius()), (int)(ball.getY() - ball.getRadius()), (int)ball.getRadius()*2,(int)ball.getRadius()*2);

		g.setColor( Color.yellow );
		g.fillOval( (int)(ball2.getX() - ball2.getRadius()), (int)(ball2.getY() - ball2.getRadius()), (int)ball2.getRadius()*2,(int)ball2.getRadius()*2);

		g.setColor( Color.black );
		g.fillOval( (int)(mallet.getX() - mallet.getRadius()), (int)(mallet.getY() - mallet.getRadius()), (int)mallet.getRadius()*2,(int)mallet.getRadius()*2);

		vx = ball.getVX();
		vy = ball.getVY();
		
		nx = ball.getX() - mallet.getX(); 
		ny = ball.getY() - mallet.getY();
		nnx = ball.getX() - ball2.getX(); 
		nny = ball.getY() - ball2.getY();
		
		vx2 = ball2.getVX();
	    vy2 = ball2.getVY();
		
		nx2 = ball2.getX() - mallet.getX(); 
		ny2 = ball2.getY() - mallet.getY();
		
		tx = ny;
		ty = (-1)*nx;
		ttx = nny;
		tty = (-1)*nnx;
		
		tx2 = ny2;
		ty2 = (-1)*nx2;
		
		v = Math.sqrt( ball.getVX()*ball.getVX() + ball.getVY()*ball.getVY() );
		v2 = Math.sqrt( ball2.getVX()*ball2.getVX() + ball2.getVY()*ball2.getVY() );
		
		cos = ( (vx*tx + vy*ty) / ( v * Math.sqrt(tx*tx+ty*ty)));
		ccos = ( (vx*ttx + vy*tty) / ( v * Math.sqrt(ttx*ttx+tty*tty)));
		
		cos2 = ( (ball2.getVX()*tx2 + ball2.getVY()*ty2) / ( v2 * Math.sqrt(tx2*tx2+ty2*ty2)));
		
		dis=Math.sqrt((nx*nx)+(ny*ny));
		ddis = Math.sqrt((nnx*nnx)+(nny*nny));
		
		dis2 = Math.sqrt((nx2*nx2)+(ny2*ny2));
		if( dis <= 30  ){
			
			vx = ((v*cos)*tx / Math.sqrt(tx*tx+ty*ty)) + ((v*Math.sqrt(1-cos*cos))*nx/ Math.sqrt(nx*nx+ny*ny));
			vy = ((v*cos)*ty / Math.sqrt(tx*tx+ty*ty)) + ((v*Math.sqrt(1-cos*cos))*ny/ Math.sqrt(nx*nx+ny*ny));
		
			g.setColor( Color.yellow );
		}
		
		if( Math.sqrt(ddis) <= 10  ){
			
			vx = ((v*ccos)*ttx / Math.sqrt(ttx*ttx+tty*tty)) + ( (v*Math.sqrt(1-ccos*ccos)) *nnx/ Math.sqrt(nnx*nnx+nny*nny) );
			vy = ((v*ccos)*tty / Math.sqrt(ttx*ttx+tty*tty)) + ((v*Math.sqrt(1-ccos*ccos))*nny/ Math.sqrt(nnx*nnx+nny*nny));
		}
		
		if( Math.sqrt(dis2) <= 30  ){
			
			vx2 = ((v2*cos2)*tx2 / Math.sqrt(tx2*tx2+ty2*ty2)) + ((v2*Math.sqrt(1-cos2*cos2))*nx2/Math.sqrt(nx2*nx2+ny2*ny2));
			vy2 = ((v2*cos2)*ty2 / Math.sqrt(tx2*tx2+ty2*ty2)) + ((v2*Math.sqrt(1-cos2*cos2))*ny2/Math.sqrt(nx2*nx2+ny2*ny2));
		}
		
		ball.setVX(vx);
		ball.setVY(vy);
		
		ball2.setVX(vx2);
		ball2.setVY(vy2);
			
	}
	public void run(){
		while(true){
			repaint();
			try{
				Thread.sleep(10);
				
				
			}catch( Exception e){
				
			}
		}
	}
	
	public void mouseDragged(MouseEvent e){
		
	}
	public void mouseMoved(MouseEvent e){
		mallet.setX(e.getX());
		mallet.setY(e.getY());
	}
}

class Ball2_4{
	
	private double x;
	private double y;
	private double vx;
	private double vy;
	private double radius;
	
	public Ball2_4( double x, double y, double vx, double vy,double radius){
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.radius = radius;
	}
	
	public void forward(){
		
		
		if( x < 0 || x + 10 > 640 )
		{
			vx = vx * (-1); 
		}
		
		if( y < 0 || y + 40 > 480 )
		{
			vy = vy * (-1); 
		}
		
		x = x + vx;
		y = y + vy;
	}
	
	public double getX(){
		return x;
	}
	
	public void setX( int x ){
		this.x = x;
	}
	
	public double getY(){
		return y;
	}
	
	public void setY( int y ){
		this.y = y;
	}
	
	public double getVX(){
		return vx;
	}
	
	public void setVX( double vx ){
		this.vx = vx;
	}
	
	public double getVY(){
		return vy;
	}
	
	public void setVY( double vy ){
		this.vy = vy;
	}
	
	public double getRadius(){
		return radius;
	}
	
	public void setRadius( double radius){
		this.radius = radius;
	}
}

class Mallet2_4{
	
	private double x;
	private double y;
	private double radius;
	
	
	public Mallet2_4( double x, double y,double radius){
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	public double getX(){
		return x;
	}
	
	public void setX( double x ){
		this.x = x;
	}
	
	public double getY(){
		return y;
	}
	
	public void setY( double y ){
		this.y = y;
	}
	
	public double getRadius(){
		return radius;
	}
	
	public void setRadius( double radius){
		this.radius = radius;
	}
}
