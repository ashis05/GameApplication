package com.example.gameapplication.gamestates;

import static com.example.gameapplication.helper.GameConstant.Maps.SCALE_MULTIPLIER;
import static com.example.gameapplication.helper.GameConstant.Maps.X_OFFSET;
import static com.example.gameapplication.helper.GameConstant.Maps.Y_OFFSET;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.gameapplication.UI.PlayingUI;
import com.example.gameapplication.entities.Character;
import com.example.gameapplication.entities.Player;
import com.example.gameapplication.entities.Weapons;
import com.example.gameapplication.entities.enemies.Vampire;
import com.example.gameapplication.helper.GameConstant;
import com.example.gameapplication.helper.HelpMethods;
import com.example.gameapplication.main.Game;
import com.example.gameapplication.main.MainActivity;
import com.example.gameapplication.maps.MapManager;
import com.example.gameapplication.maps.Portal;

public class Playing extends BaseState implements GameStates{

    private boolean movePlayer, attacking, attackChecked;
    private MapManager mapManager;
    //private BuildingManager buildingManager;
    private Player player;
//    private ArrayList<Vampire> vampire;
    private float cameraX, cameraY;
    private PointF lastTouchDiff;
    private PlayingUI playingUI;
    private RectF weaponBox = null;
    private Paint redPaint = new Paint();
    private Portal portal;
    private RectF portalHitbox;
    private boolean portalJustPassed;


    public Playing(Game game){
        super(game);
        mapManager = new MapManager(this);
        initCamera();
        // buildingManager = new BuildingManager();
        player = new Player();
//        vampire = new ArrayList<>();
        playingUI = new PlayingUI(this);
        redPaint.setStrokeWidth(1);
        redPaint.setColor(Color.RED);
        redPaint.setStyle(Paint.Style.STROKE);
//        for (int i = 0; i < 5; i++)
//            spawnVampire();
       // updateWeaponHitbox();
    }

    private void initCamera() {
        cameraX = MainActivity.PhoneWidth / 2 - 2496 ;
        cameraY = MainActivity.PhoneHeight / 2 - 1536 ;
    }

    public void setCameraValues(PointF cameraPos) {
        this.cameraX = cameraPos.x;
        this.cameraY = cameraPos.y;
    }
//
//    public void spawnVampire() {
//        vampire.add(new Vampire(new PointF(player.getHitbox().left - cameraX, player.getHitbox().top - cameraY)));
//
//    }

    @Override
    public void update(double delta) {
        updatePlayerMove(delta);
        player.update(delta, movePlayer);
        mapManager.setCameraValues(cameraX, cameraY);
        checkForPortal();
        updateWeaponHitbox();


        if (attacking) if (!attackChecked)
            checkAttack();

        for (Vampire v : mapManager.getCurrentMap().getVampireArrayList())
            if (v.isActive())
                v.update(delta, mapManager.getCurrentMap());

       // buildingManager.setCameraValues(cameraX, cameraY);
    }

    private void checkForPortal() {
        Portal portalPlayerIsOn = mapManager.isPlayerOnPortal(player.getHitbox());

        if (portalPlayerIsOn != null) {
            if (!portalJustPassed)
                mapManager.changeMap(portalPlayerIsOn.getPortalConnectedTo());
        } else
            portalJustPassed = false;
    }


    private void checkAttack() {
        RectF attackBoxWithoutCamera = new RectF(weaponBox);
        attackBoxWithoutCamera.left -= cameraX;
        attackBoxWithoutCamera.top -= cameraY;
        attackBoxWithoutCamera.right -= cameraX;
        attackBoxWithoutCamera.bottom -= cameraY;

        for (Vampire v : mapManager.getCurrentMap().getVampireArrayList())
            if (attackBoxWithoutCamera.intersects(v.getHitbox().left, v.getHitbox().top, v.getHitbox().right, v.getHitbox().bottom))
                v.setActive(false);

        attackChecked = true;

    }

    private void updateWeaponHitbox() {
        PointF pos = getWeaponPos();
        float width = getWeaponWidth(), height = getWeaponHeight();
        weaponBox = new RectF(pos.x, pos.y, pos.x + width, pos.y + height);
    }

    private float getWeaponHeight() {
        return switch (player.getFaceDir()){
            case GameConstant.Face_Dir.UP, GameConstant.Face_Dir.DOWN -> Weapons.SWORD.getHeight();

            case GameConstant.Face_Dir.LEFT, GameConstant.Face_Dir.RIGHT -> Weapons.SWORD.getWidth();

            default -> {throw new IllegalStateException("Unexpected Value:" + player.getFaceDir());}
        };
    }

    private float getWeaponWidth() {
        return switch (player.getFaceDir()){
            case GameConstant.Face_Dir.UP, GameConstant.Face_Dir.DOWN -> Weapons.SWORD.getWidth();

            case GameConstant.Face_Dir.LEFT, GameConstant.Face_Dir.RIGHT -> Weapons.SWORD.getHeight();

            default -> {throw new IllegalStateException("Unexpected Value:" + player.getFaceDir());}
        };
    }

    private PointF getWeaponPos() {
        return switch (player.getFaceDir()){
            case GameConstant.Face_Dir.UP -> new PointF((float) (player.getHitbox().left + 2.75f * GameConstant.Maps.SCALE_MULTIPLIER - Y_OFFSET ), player.getHitbox().top - Weapons.SWORD.getHeight() - Y_OFFSET);

            case GameConstant.Face_Dir.DOWN -> new PointF(player.getHitbox().left - 0.75f * GameConstant.Maps.SCALE_MULTIPLIER, player.getHitbox().bottom);

            case GameConstant.Face_Dir.LEFT -> new PointF(player.getHitbox().left  - Weapons.SWORD.getHeight() - X_OFFSET, player.getHitbox().bottom - Weapons.SWORD.getWidth() - 0.75f * GameConstant.Maps.SCALE_MULTIPLIER);

            case GameConstant.Face_Dir.RIGHT -> new PointF(player.getHitbox().right + X_OFFSET, player.getHitbox().bottom - Weapons.SWORD.getWidth() - 0.75f * GameConstant.Maps.SCALE_MULTIPLIER);

            default -> {throw new IllegalStateException("Unexpected Value:" + player.getFaceDir());}
        };
    }

    @Override
    public void render(Canvas c) {
        mapManager.drawMap(c);
        mapManager.drawBuildings(c);
//        buildingManager.drawBuilding(c);
        for (Vampire v : mapManager.getCurrentMap().getVampireArrayList())
            if (v.isActive())
                drawCharacter(c, v);
        drawPlayer(c);
        if (mapManager.getCurrentMap().equals(mapManager.getOutsideMap())) {
            mapManager.drawOutsideElements(c);
        }else {
            mapManager.drawCaveElements(c);
        }

        playingUI.drawUI(c);

    }


    private void drawPlayer(Canvas c) {
        c.drawBitmap(Weapons.SHADOW.getWeaponImg(), player.getHitbox().left, player.getHitbox().bottom - 5.0f * SCALE_MULTIPLIER, null);

        c.drawBitmap(
                player.getGameCharType().getSprite(getAniIndex(), player.getFaceDir()),
                player.getHitbox().left - X_OFFSET,
                player.getHitbox().top - Y_OFFSET,
                null);
        //c.drawRect(player.getHitbox(),redPaint);
        if(attacking)
            drawWeapon(c);
    }

    private int getAniIndex(){
        if (attacking)
            return 4;
        return player.getAniIndex();
    }

    private void drawWeapon(Canvas c) {
        c.rotate(getWeaopnrRotation(), weaponBox.left, weaponBox.top);
        c.drawBitmap(
                Weapons.SWORD.getWeaponImg(),
                weaponBox.left + weaponAdjutLeft(),
                weaponBox.top + weaponAdjutTop(),
                null);
        c.rotate(getWeaopnrRotation() * -1, weaponBox.left, weaponBox.top);
        //c.drawRect(weaponBox, redPaint);
    }

    private float weaponAdjutTop() {
        return switch (player.getFaceDir()){
            case GameConstant.Face_Dir.LEFT, GameConstant.Face_Dir.UP -> - Weapons.SWORD.getHeight();

            default -> 0;
        };
    }

    private float weaponAdjutLeft() {
        return switch (player.getFaceDir()){
            case GameConstant.Face_Dir.UP, GameConstant.Face_Dir.RIGHT -> - Weapons.SWORD.getWidth();

            default -> 0;
        };
    }

    private float getWeaopnrRotation() {
        return switch (player.getFaceDir()){
            case GameConstant.Face_Dir.LEFT -> 90;
            case GameConstant.Face_Dir.UP -> 180;
            case GameConstant.Face_Dir.RIGHT -> 270;

            default -> 0;
        };

    }

    public void drawCharacter(Canvas canvas, Character c){
        canvas.drawBitmap(Weapons.SHADOW.getWeaponImg(), c.getHitbox().left + cameraX, c.getHitbox().bottom + cameraY - 5.0f * SCALE_MULTIPLIER, null);

        canvas.drawBitmap(
                c.getGameCharType().getSprite(c.getAniIndex(), c.getFaceDir()),
                c.getHitbox().left + cameraX - X_OFFSET,
                c.getHitbox().top + cameraY - Y_OFFSET,
                null);
        //canvas.drawRect(c.getHitbox().left + cameraX, c.getHitbox().top + cameraY,c.getHitbox().right + cameraX,c.getHitbox().bottom + cameraY, redPaint);
    }

    private void updatePlayerMove(double delta) {
        if (!movePlayer)
            return;

        float baseSpeed = (float) (delta * 300);
        float ratio = Math.abs(lastTouchDiff.y) / Math.abs(lastTouchDiff.x);
        double angle = Math.atan(ratio);

        float xSpeed = (float) Math.cos(angle);
        float ySpeed = (float) Math.sin(angle);

        if (xSpeed > ySpeed) {
            if(lastTouchDiff.x > 0)player.setFaceDir(GameConstant.Face_Dir.RIGHT);
            else player.setFaceDir(GameConstant.Face_Dir.LEFT);
        }
        else{
            if (lastTouchDiff.y > 0)player.setFaceDir(GameConstant.Face_Dir.DOWN);
            else player.setFaceDir(GameConstant.Face_Dir.UP);
        }

        if (lastTouchDiff.x < 0){xSpeed *= -1;}
        if (lastTouchDiff.y < 0){ySpeed *= -1;}

        float deltaX = xSpeed * baseSpeed * -1;
        float deltaY = ySpeed * baseSpeed * -1;

        float deltaCameraX = cameraX * -1 + deltaX * -1;
        float deltaCameraY = cameraY * -1 + deltaY * -1;

        if (HelpMethods.CanWalkHere(player.getHitbox(), deltaCameraX, deltaCameraY, mapManager.getCurrentMap())) {
            cameraX += deltaX;
            cameraY += deltaY;
        }

    }

    public void setPlayerMoveTrue(PointF lastTouchDiff){
        movePlayer = true;
        this.lastTouchDiff = lastTouchDiff;
    }

    public void setPlayerMoveFalse() {
        movePlayer = false;
        player.resetAnimation();
    }


    @Override
    public void touchEvents(MotionEvent event) {
        playingUI.touchEvents(event);
    }

    public void setGameState() {
        game.setCurrentGameState(Game.GameState.MENU);
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
        if (!attacking){
            attackChecked = false;
        }
    }

    public void setPortalJustPassed(boolean doorwayJustPassed) {
        this.portalJustPassed = doorwayJustPassed;
    }
}
