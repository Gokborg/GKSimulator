package net;

import core.Game;
import core.GameObject;
import gameobjects.Block;
import gameobjects.BlockType;
import gameobjects.Player;
import gameobjects.Wire;
import net.packets.BlockActionPacket;
import net.packets.MovePlayerPacket;
import net.packets.NewPlayerPacket;
import net.packets.Packet;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class Client extends Thread
{
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Game game;
    private Queue<Packet> packetQueue;
    private boolean connected;

    public Client(Game game)
    {
        this.game = game;
        packetQueue = new LinkedList<>();
    }
    public void connect(String address, int port)
    {
        try
        {
            socket = new Socket(address, port);
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Tried connecting to a local host server, failed to connect, all good!");
        }
    }

    //This will always be looking to see if the server is sending something and react to it
    @Override
    public void run()
    {
        try
        {
            System.out.println("Waiting to recieve: " + System.currentTimeMillis());
            char recievedChar = in.readChar();
            System.out.println("Recieved: " + recievedChar);
            if(recievedChar == 'a') //confirmation that server is up and running and ready to recieve
            {
                connected = true;
                sendPlayerNewPacket(game.getScene().getPlayer());
                while(connected)
                {
                    int packetType = in.readInt();
                    switch(packetType)
                    {
                        case Packet.NEW_PLAYER_PACKET_ID:
                            packetQueue.add(new NewPlayerPacket(in)); break;
                        case Packet.MOVE_PLAYER_PACKET_ID:
                            packetQueue.add(new MovePlayerPacket(in)); break;
                        case Packet.BLOCK_ACTION_PACKET_ID:
                            packetQueue.add(new BlockActionPacket(in)); break;
                    }
                }
            }
        }
        catch (IOException e)
        {
            disconnect();
            e.printStackTrace();
        }
    }

    public void update()
    {
        if(connected)
        {
            for (int i = 0; i < packetQueue.size(); i++)
            {
                Packet packet = packetQueue.remove();
                //TODO: Remove debug printing of packets
                System.out.println(packet.toString());
                if(packet instanceof NewPlayerPacket)
                {
                    NewPlayerPacket newPlayerPacket = (NewPlayerPacket) packet;
                    GameObject possiblePlayer = game.getScene().getGameObject(newPlayerPacket.getUUID());
                    //Means some new player joined, we dont have that player in the scene
                    if(possiblePlayer == null)
                    {
                        game.getScene().add(
                                new Player(game, newPlayerPacket.getUUID(), newPlayerPacket.getName(), newPlayerPacket.getX(), newPlayerPacket.getY(), false)
                        );
                        sendPlayerNewPacket(game.getScene().getPlayer());
                    }
                }
                else if(packet instanceof MovePlayerPacket)
                {
                    MovePlayerPacket movePlayerPacket = (MovePlayerPacket) packet;
                    Player player = (Player) game.getScene().getGameObject(movePlayerPacket.getUUID());
                    switch(movePlayerPacket.getMovementType())
                    {
                        case MovePlayerPacket.MOVING_LEFT: player.setAnimation("Left"); break;
                        case MovePlayerPacket.MOVING_RIGHT: player.setAnimation("Right"); break;

                        case MovePlayerPacket.IDLE:
                        case MovePlayerPacket.MOVING_UP:
                        case MovePlayerPacket.MOVING_DOWN:
                            player.setAnimation("Idle");
                    }
                    player.setX(movePlayerPacket.getX());
                    player.setY(movePlayerPacket.getY());
                }
                else if(packet instanceof BlockActionPacket)
                {
                    BlockActionPacket blockActionPacket = (BlockActionPacket) packet;
                    switch(blockActionPacket.getAction())
                    {
                        case BlockActionPacket.ACTION_PLACE:
                            if(blockActionPacket.getBlockId() == BlockType.WIRE.ordinal())
                            {
                                Wire wire = new Wire(game, UUID.randomUUID(), blockActionPacket.getBlockX(), blockActionPacket.getBlockY());
                                game.getGrid().add(wire);
                            }
                            break;

                        case BlockActionPacket.ACTION_REMOVE:
                            Block blockToRemove = game.getGrid().getBlock(blockActionPacket.getBlockX(), blockActionPacket.getBlockY());
                            game.getGrid().remove(blockToRemove);
                            break;

                    }
                }
            }
        }
    }

    public void sendPlayerMovePacket(Player player, int movementType)
    {
        if(connected)
        {
            new MovePlayerPacket(player.getUUID(), movementType, player.getX(), player.getY()).write(out);
        }
    }

    public void sendBlockActionPacket(Block block, int action)
    {
        if(connected)
        {
            new BlockActionPacket(block.getBlockType(), action, block.getBlockX(), block.getBlockY()).write(out);
        }
    }

    public void sendPlayerNewPacket(Player player)
    {
        if(connected)
        {
            new NewPlayerPacket(player.getUUID(), player.getName(), player.getX(), player.getY()).write(out);
        }
    }

    public void disconnect()
    {
        connected = false;
        try
        {
            in.close();
            out.close();
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
