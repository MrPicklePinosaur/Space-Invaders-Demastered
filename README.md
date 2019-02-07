# Space-Invaders-Demastered
Created by Shrey Mahey and Daniel Liu

Class Breakdown  
--> Main  
--> Entity - Abstract class; creates box2d body by taking in a sprite  
	- Player
	- Enemy - includes AI  
	- Projectile  
--> Renderer - includes debugRenderer and camera  
--> Global - includes and variables that need to be access from any part of the project; also handles input 
--> Map  
--> CollisionListener - implementation of ContactListener

Journal
Edit as of 2/2/19 by Shrey Mahey:
	Did rough work on easyenemy movement logic using joints... should work in THEORY, but unlikely to.
	Cleaned up a few classes to make this actually runnable, but the game world/objects arent actually created in main yet.
	Added stuff to entity and global to make creating enemies easier.
	Changed main to fix the aforementioned errors so I could run the program.
	Messed around with player a little bit, but I don't think much was changed.
