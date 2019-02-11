# Space-Invaders-Demastered
Created by Shrey Mahey and Daniel Liu

Simple Game project made for ISC4U

Controls  
--> Mouse movement - rotates player  
--> Left click - shoot  
--> Escape - opens pause menu  
--> I - toggle autofire  
--> L - toggle camera lock

Class Breakdown  
--> Main  
--> Entity - Abstract class; creates box2d body by taking in a sprite  
	- Player  
	- Enemy - includes AI  
	- Projectile  
--> Renderer - includes debugRenderer and camera  
--> Global - includes and variables that need to be access from any part of the project; also handles input  
--> Map - handles difficulty and enemy spawning  
--> CollisionListener - implementation of ContactListener  
--> AssetLoader - handles body disposal, garbage collection, and async loading  
--> UI - graphical interface  
--> MusicPlayer - does what you think it does  
