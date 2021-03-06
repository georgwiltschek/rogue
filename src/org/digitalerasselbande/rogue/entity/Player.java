package org.digitalerasselbande.rogue.entity;

import org.digitalerasselbande.rogue.game.Game;
import org.digitalerasselbande.rogue.map.Map;
import org.newdawn.slick.Input;

public class Player extends Entity {
		
	int next;
	int last = 0;
	Map map;
	
	public Player(Map map) {
		setSymbol("@");
		this.map = map;
		this.setType(EntityType.PLAYER);
	}
	
	public void handleInput(int key) {
		next = key;
	}
	
	public void update() {
		if (next == 0) {
			return;
		}
		
		int new_x, new_y;
		new_x = this.getPos_x();
		new_y = this.getPos_y();
		
		if (next == Input.KEY_UP) {
			new_y = (this.getPos_y() - 1);
		}
		else if (next == Input.KEY_DOWN) {
			new_y = (this.getPos_y() + 1);
		}
		else if (next == Input.KEY_LEFT) {
			new_x = (this.getPos_x() - 1);
		}
		else if (next == Input.KEY_RIGHT) {
			new_x = (this.getPos_x() + 1);
		}
		else if (next == Input.KEY_SPACE) {
			switch (last) {
				case Input.KEY_UP:
					new_y -= 1;
					break;
				case Input.KEY_DOWN:
					new_y += 1;
					break;
				case Input.KEY_LEFT:
					new_x -= 1;
					break;
				case Input.KEY_RIGHT:
					new_x += 1;
					break;					
			}
			map.fill(new_x, new_y);
			last = next;
			return;
//			if(!map.collidesEntity(new_x, new_y)) {
//				map.fill(new_x, new_y);
//			}
		}		
		else {
			next = 0;
		}
		
		if ((new_x >= 0) && (new_x < Game.WORLD_WIDTH) && (new_y >= 0)
				&& (new_y < Game.WORLD_HEIGHT)) {
			if (!map.collidesEntity(new_x, new_y)) {
				map.collidesItem(new_x, new_y);
				this.setPos(new_x, new_y);
			}
			else if (map.collidesWall(new_x, new_y) && Game.PLAYER_CAN_DIG) {
				map.dig(new_x, new_y);
				this.setPos(new_x, new_y);
			}
		}
		last = next;
	}
}
