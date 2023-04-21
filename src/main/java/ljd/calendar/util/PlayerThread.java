package ljd.calendar.util;

public class PlayerThread implements Runnable {

    private Player player;

    private  int type;

    private  String text;

    public PlayerThread(Player player,String text,int type) {
        this.player=player;
        this.type=type;
        this.text=text;
    }

    public void run() {

        if (type==1){
            System.out.println(text);
            player.playMusic();

        }else {
          player.setPause(true);
        }



    }
}
