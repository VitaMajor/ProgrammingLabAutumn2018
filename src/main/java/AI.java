import units.BaseHero;

public class AI {
    private Game game;

    private boolean checkMove (double move, int right, int up, int i, int j) {
        return move > (Math.sqrt(Math.pow(game.playerOneX - (game.playerTwoX + (i * right)), 2)
                + Math.pow(game.playerOneY - (game.playerTwoY + (j * up)), 2))) && checkMoveRight(right, i)
                && checkMoveUp(up, j);
    }

    private boolean checkMoveLeave (double move, int right, int up, int i, int j) {
        return move > (Math.sqrt(Math.pow(game.playerOneX - (game.playerTwoX + (i * right)), 2)
                + Math.pow(game.playerOneY - (game.playerTwoY + (j * up)), 2))) && checkMoveRight(right, i)
                && checkMoveUp(up, j);
    }

    private boolean checkMoveUp (int up, int j) {
        return game.playerTwoY + (j * up) >= 0 && game.playerTwoY + (j * up) <= game.getWorld().length - 1;
    }

    private boolean checkMoveRight (int right, int i) {
        return game.playerTwoX + (i * right) >= 0 && game.playerTwoX + (i * right) <= game.getWorld().length - 1;
    }

    private boolean checkPlace (int right, int up, int i, int j) {
        return game.playerTwoX + (i * right) == game.playerOneX && game.playerTwoY + (j * up) == game.playerOneY;
    }

    private boolean checkFreePlace (int right, int up, int i, int j) {
        return game.world[game.playerTwoX + (i * right)][game.playerTwoY + (j * up)] == 0;
    }

    private boolean moviesCheck (BaseHero bot, int i, int j) {
        return bot.getMoves() >= (Math.sqrt(Math.pow(i, 2)
                + Math.pow(j, 2)));
    }

    public void move(BaseHero player, int moveX, int moveY) {
        double move = 1000;
        double moveLeave = 0;
        if (game.mouseManager.radiusAttackCheck(player,game.playerOneX,game.playerOneY,game.playerTwoX,game.playerTwoY)){
            game.mouseManager.attack(player, game.playerOne, game.playerTwoX, game.playerTwoY, game.playerOneX, game.playerOneY);
        } else {
            for (int up = -1; up <= 1; up += 2) {
                for (int right = -1; right <= 1; right += 2) {
                    for (int i = 0; i < game.playerTwo.getMoves(); i++) {
                        for (int j = 0; j < game.playerTwo.getMoves(); j++) {
                            if (player.getHp() < 15 && player.getHeal() > 0) {
                                if (moviesCheck(player, i, j) && checkMoveLeave(moveLeave, right, up, i, j)) {
                                    if (checkPlace(right, up, i, j)) {
                                        continue;
                                    } else {
                                        if (checkFreePlace(right, up, i, j)) {
                                            moveX = game.playerTwoX + (i * right);
                                            moveY = game.playerTwoY + (j * up);
                                            moveLeave = (Math.sqrt(Math.pow(game.playerOneX - (game.playerTwoX + (i * right)), 2)
                                                    + Math.pow(game.playerOneY - (game.playerTwoY + (j * up)), 2)));
                                        }
                                    }
                                }
                                game.mouseManager.heal(player);
                            } else {
                                if (moviesCheck(player, i, j) && checkMove(move, right, up, i, j)) {
                                    if (checkPlace(right, up, i, j)) {
                                        continue;
                                    } else {
                                        if (checkFreePlace(right, up, i, j)) {
                                            moveX = game.playerTwoX + (i * right);
                                            moveY = game.playerTwoY + (j * up);
                                            move = (Math.sqrt(Math.pow(game.playerOneX - (game.playerTwoX + (i * right)), 2)
                                                    + Math.pow(game.playerOneY - (game.playerTwoY + (j * up)), 2)));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            System.out.println(moveX + " " + moveY + " " + move);
            game.playerTwoX = moveX;
            game.playerTwoY = moveY;
        }
        game.mouseManager.movePlayers--;
        if (game.mouseManager.movePlayers == 0) {
            game.mouseManager.movePlayers = 2;
            game.mouseManager.moveCounter++;
        }
    }
    public AI(Game game){
        this.game = game;
    }
}
