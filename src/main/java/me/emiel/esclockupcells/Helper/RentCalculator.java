package me.emiel.esclockupcells.Helper;

import me.emiel.esclockupcells.EscLockupCells;
import me.emiel.esclockupcells.Models.Cell;
import me.emiel.esclockupcells.Models.Size;

public class RentCalculator {
    public static int GetDefaultRent(Size size){
        EscLockupCells plugin = EscLockupCells.get_instance();
        return (plugin.getConfig().getInt( "rent." + size.name().toLowerCase()));
    }

    public static int GetRent(Cell cell) {
        int secondsLeft = cell.get_timeLeft();
        int rent = GetDefaultRent(cell.get_size());
        //(5441799) / (86400*2) *64
        int newRent = (int) (secondsLeft / (86400f * 2f) * rent);
        return Math.max(newRent, rent);
    }

}
