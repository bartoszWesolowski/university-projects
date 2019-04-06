package pl.gameTutorial.main;

import java.awt.Graphics;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import pl.gameTutorial.gameObjects.GameObject;
import pl.gameTutorial.gameObjects.ID;


/**
 * Class to handle all of the objects in game. Lopps throught all of the objects in the game, upgrades them and then render;
 * @author Bartek
 * 
 */
public class Handler {

	LinkedList<GameObject> objects = new LinkedList<GameObject>();

	public void tick() {
		for (int i = 0; i < objects.size(); i++) {
			GameObject tempObject = objects.get(i);
			tempObject.tick();
		}
	}

	public void render(Graphics g) {
		for (int i = 0; i < objects.size(); i++) {
			GameObject tempObject = objects.get(i);
			tempObject.render(g);
		}
	}

	public boolean add(GameObject object) {
		try {
			if (objects.add(object)) {
				Collections.sort(objects);
				return true;
			}
		} catch (Exception e) {
			System.err.println("Błąd przy dodawaniu obiektu złapany!");
			//e.printStackTrace();
		}
		
		return false;
	}

	public boolean remove(Object o) {
		return objects.remove(o);
	}

	public int size() {
		return objects.size();
	}

	public GameObject get(int index) {
		return objects.get(index);
	}

	public Iterator<GameObject> iterator() {
		return objects.iterator();
	}

	/**
	 * Removes objects from handler leaving object with id given as parametr
	 * @param except ID of objects that should not be removed
	 */
	public void clear(ID except) {
		for (GameObject g : objects) {
			if (!(g.getId() == except)) {
				objects.remove(g);
			}
		}
	}

	public void clear() {
		objects.clear();
	}

	
}
