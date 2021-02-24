import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


class Coord{
	public double x;
	public double y;


	public Coord(){
		x = 0;
		y = 0;
	}

	public Coord(double a, double b){
		x = a;
		y = b;
	}

	public void normalize(){
		double len = Math.sqrt(x*x + y*y);
		if(len != 0){
			x /= len;
			y /= len;
		}
	}

	public void limit(double max){
		double len = Math.sqrt(x*x + y*y);
		if(max < len){
			normalize();
			multiply(max);
		}
	}

	public void add(Coord n){
		x += n.x;
		y += n.y;
	}

	public void add(double a, double b){
		x += a;
		y += b;
	}
	
	public void multiply(double num){
		x *= num;
		y *= num;
	}

	public void set(int a, int b){
		x = a;
		y = b;
	}

	public void sub(Coord num){
		x = x-num.x;
		y = y-num.y;
	}

	public static Coord sub(Coord a, Coord b){
		Coord res = new Coord();
		res.x = a.x - b.x;
		res.y = a.y - b.y;
	  return res;
	}

	public static Coord add(Coord a, Coord b){
		Coord res = new Coord();
		res.x = a.x+b.x;
		res.y = a.y+b.y;
		return res;
	}

	public double heading(){
		return Math.atan2(x,y);
	}

}

class Particle{
	//You may want to add some functions for drawing a Particle
	//  you could give particles different colors
	//  you could give different particles different sizes


	//change these things if you want
	double maxForce = 50;
	double maxSpeed = 100;
	Coord acceleration = new Coord();
	Coord velocity = new Coord();
	Coord position = new Coord();
	Coord force = new Coord();
	Coord target = new Coord();

	public Particle(){
	}
	public Particle(int x, int y){
		this.position.x = x;
		this.position.y = y;
	}

	public void updateStuff(double force, double speed){
		maxForce = force;
		maxSpeed = speed;
	}

	//does the old point mass physics
	//dt is the change in time -- it should be in seconds! ie a fraction of a second
	public void update(double dt){
        acceleration.add(force);
        acceleration.multiply(dt);
        velocity.add(acceleration);
        position.add(velocity.x*dt,velocity.y*dt);
        force.set(0,0);
        acceleration.set(0,0);
	}

	public void seek(Coord target){
		Coord end = Coord.sub(target, position);
		end.normalize();
		end.multiply(maxForce);
		end.sub(velocity);
		end.limit(maxSpeed);
		force.add(end);
	}

	public void flee(Coord target){
		Coord end = Coord.sub(position,target);
		end.normalize();
		end.multiply(maxForce);
		end.sub(velocity);
		end.limit(maxSpeed);
		force.add(end);
	}

	public void wander(){
	double wanderTheta = 0;	
	  wanderTheta +=(Math.random()*0.2)-0.1;

		Coord circle = new Coord(velocity.x, velocity.y);
		circle.normalize();
		circle.multiply(200);
		circle.add(position);

		double heading = velocity.heading();
		Coord pointOnCircle = new Coord(100*Math.cos(wanderTheta+heading), 100*Math.sin(wanderTheta+heading));
		pointOnCircle.add(circle);
		seek(pointOnCircle);
	}

}


public class Animation extends JPanel 
					   implements  MouseListener, ActionListener{
	//create a frame and make Animation a jpanel
	//use the Timer in javax.swing.Timer to perform some animation
	//creates a timer and 3 particles to seek target
	//c is the target coordinate
	Timer timer;
	Particle particle = new Particle();
	Particle particle2 = new Particle(400,0);
	Particle particle3 = new Particle(0,400);
	Coord c = new Coord();
	String a = "Seek";
	public void actionPerformed(ActionEvent e){
		//dividing changes the rate at which the 
		//different particles update and seek
		double dt = 1;
	 if(e.getActionCommand() == "time"){
		if(a == "Seek"){
		particle.update(dt/5);
           particle.seek(c);
           particle2.update(dt/6);
           particle2.seek(c);
           particle3.update(dt/7);
           particle3.seek(c);

		}else if(a == "Flee"){
		particle.update(dt/5);
           particle.flee(c);
           particle2.update(dt/6);
           particle2.flee(c);
           particle3.update(dt/7);
           particle3.flee(c);

		}else if(a == "Wander"){
		particle.update(dt/5);
           particle.wander();
           particle2.update(dt/6);
           particle2.wander();
           particle3.update(dt/7);
		   particle3.wander();

		}
	 }else if(e.getActionCommand() == "Wander"){
             particle.update(dt/5);
          particle.wander();
          particle2.update(dt/6);
          particle2.wander();
          particle3.update(dt/7);
          particle3.wander();
		  a = "Wander";
         }else if(e.getActionCommand() == "Seek"){
          particle.update(dt/5);
          particle.seek(c);
          particle2.update(dt/6);
          particle2.seek(c);
          particle3.update(dt/7);
          particle3.seek(c);
		  a = "Seek";
         }else if(e.getActionCommand() == "Flee"){
          particle.update(dt/5);
          particle.flee(c);
          particle2.update(dt/6);
          particle2.flee(c);
          particle3.update(dt/7);
          particle3.flee(c);
		  a = "Flee";
         }else if(e.getActionCommand() == "inc. force"){
			particle.maxForce += 10;
			particle2.maxForce += 10;
			particle3.maxForce += 10;
		 }else if(e.getActionCommand() == "dec. force"){
			particle.maxForce -= 10;
			particle2.maxForce -=10;
			particle3.maxForce -=10;
		 }else if(e.getActionCommand() == "inc. speed"){
			particle.maxSpeed += 10;
             particle2.maxSpeed += 10;
             particle3.maxSpeed += 10;

		 }else if(e.getActionCommand() == "dec. speed"){
			particle.maxSpeed -= 10;
             particle2.maxSpeed -=10;
             particle3.maxSpeed -=10;
		 }

		repaint();
	}
	public void mouseClicked(MouseEvent e){
	  //gets the position in the window that the mouse was clicked
	  //updates the Coord storing that info
	    c.x = e.getX();
        c.y = e.getY();
		repaint();
	}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseMoved(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}

	//particle.seek(~) will apply the forces to move the particle in the direciton of the target
	public Animation(){
		timer = new Timer(75,this);
		timer.setActionCommand("time");
		timer.start();
		addMouseListener(this);
		
	}
	
	public void updateAction(String str){
		a = str;
	}

	public void paintComponent(Graphics og){
		og.setColor(Color.BLACK);
		og.fillRect(0,0,this.getWidth(),this.getHeight());
		og.setColor(Color.GREEN);
		//casts doubles into ints to satisfy the int in fillOval
		int x = (int)particle.position.x;
		int y = (int)particle.position.y;
		int xxx = (int)particle2.position.x;
		int yyy = (int)particle2.position.y;
		int xxxx = (int)particle3.position.x;
		int yyyy = (int)particle3.position.y;
		int xx = (int)c.x;
		int yy = (int)c.y;
		//draws each particle
		og.fillOval(x,y,30,30);
		og.setColor(Color.WHITE);	
		og.fillOval(xx,yy,30,30);
		og.setColor(Color.BLUE);
		og.fillOval(xxx,yyy,30,30);
		og.setColor(Color.RED);
		og.fillOval(xxxx,yyyy,30,30);
	}

	public static void main(String[] args){
	  //sets up the window
	 /*   File in = newFile(args[1]);
		BufferedReader br = new BufferedReader(new FileReader(in));
		String st;
		st = br.readLine()
	*/		
		

		JFrame frame = new JFrame("Hello World");
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();

		frame.setSize(1200,1200);
		Animation a = new Animation();
		a.setDoubleBuffered(true);
		JPanel gui = new JPanel();

		 String wanderString = "Wander";
         String seekString = "Seek";
         String fleeString = "Flee";
         JButton upForce = new JButton("inc. force");
		 upForce.addActionListener(a);
		 gui.add(upForce);
		 JButton dForce = new JButton("dec. force");
		 dForce.addActionListener(a);
		 gui.add(dForce);
		 JButton upSpeed = new JButton("inc. speed");
		 upSpeed.addActionListener(a);
		 gui.add(upSpeed);
		 JButton dSpeed = new JButton("dec. speed");
		 dSpeed.addActionListener(a);
		 gui.add(dSpeed);
		 JRadioButton wander = new JRadioButton(wanderString);
         JRadioButton seek = new JRadioButton(seekString);
         JRadioButton flee = new JRadioButton(fleeString);
             wander.setMnemonic(KeyEvent.VK_W);
             wander.setActionCommand(wanderString);
             seek.setMnemonic(KeyEvent.VK_S);
             seek.setActionCommand(seekString);
             flee.setMnemonic(KeyEvent.VK_F);
             flee.setActionCommand(fleeString);
             wander.addActionListener(a);
             seek.addActionListener(a);
             flee.addActionListener(a);
             gui.add(wander);
             gui.add(seek);
             gui.add(flee);
 
             ButtonGroup group = new ButtonGroup();
             group.add(wander);
             group.add(seek);
             group.add(flee);


		p1.add(a);
		p1.setPreferredSize(new Dimension(900,900));
		p2.add(gui);
		p2.setPreferredSize(new Dimension(200,200));
		frame.add(p1,BorderLayout.CENTER);
		frame.add(p2, BorderLayout.PAGE_END);
		frame.getContentPane().add(a);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
