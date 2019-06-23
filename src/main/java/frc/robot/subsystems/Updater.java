package frc.robot.subsystems;

public abstract class Updater implements Runnable
{
    @Override
    public final void run()
    {
        updateNumbers();

    }
    public abstract void updateNumbers();
}