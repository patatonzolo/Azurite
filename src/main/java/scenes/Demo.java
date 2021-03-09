package scenes;

import ecs.*;
import graphics.*;
import graphics.renderer.LightmapRenderer;
import physics.AABB;
import tiles.Spritesheet;
import tiles.Tilesystem;
import physics.Transform;
import util.Assets;
import util.Engine;
import util.Scene;
import util.Utils;

import static graphics.Graphics.setDefaultBackground;

public class Demo extends Scene {
    public static void main (String[] args) {
        Engine.init("test", 0.2F);
    }

    Spritesheet a;
    Spritesheet b;
    Tilesystem t;
    GameObject player;
    GameObject booper;
    LightmapRenderer tr;

    public void awake() {
        camera = new Camera();
        setDefaultBackground(Color.BLACK);

        tr = new LightmapRenderer();
        tr.init();
        registerRenderer(tr);

        a = new Spritesheet(Assets.getTexture("src/assets/images/tileset.png"), 16, 16, 256, 0);
        b = new Spritesheet(Assets.getTexture("src/assets/images/walls.png"), 16, 16, 256, 0);
        t = new Tilesystem(a, b, 31, 15, 200, 200);

        booper = new GameObject("Booper", new Transform(800, 800, 100, 100), 2);
        booper.addComponent(new SpriteRenderer(a.getSprite(132))); // a.getSprite(132)
        booper.addComponent(new AABB());

        player = new GameObject("Player", new Transform(600, 600, 100, 100), 2);
        player.addComponent(new PointLight(new Color(250, 255, 181), 30));
        player.addComponent(new AABB());
        player.addComponent(new SpriteRenderer(a.getSprite(132)));
        player.addComponent(new CharacterController());
    }

    public void update() {
        super.update();
        player.getComponent(PointLight.class).intensity = Utils.map((float)Math.sin(Engine.millis()/600), -1, 1, 100, 140);

        camera.smoothFollow(player.getTransform());
    }

    @Override
    public void render() {
        tr.bindLightmap();
        super.render();
        //  tr.framebuffer.blitColorBuffersToScreen();
    }
}
