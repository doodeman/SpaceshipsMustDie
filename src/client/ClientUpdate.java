package client;

import shared.Jsonable;

public class ClientUpdate extends Jsonable
{
	public boolean forward, backward, left, right, fire, up, down, rollLeft, rollRight; 
	public int clientId;
	public ClientUpdate(boolean forward, boolean backward, boolean left,
			boolean right, boolean fire, boolean up, boolean down, boolean rollLeft, boolean rollRight, int clientId) {
		this.forward = forward;
		this.backward = backward;
		this.left = left;
		this.right = right;
		this.fire = fire;
		this.up = up;
		this.down = down;
		this.rollLeft = rollLeft;
		this.rollRight = rollRight;
		this.clientId = clientId;
		
	} 
}
