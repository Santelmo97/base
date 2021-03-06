import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

import java.time.LocalDateTime;

public class TrainTachograph {
    private Table<LocalDateTime, Integer, Integer> table;

    public TrainTachograph(){
        table= TreeBasedTable.create();
    }
    public void add(Integer joystickPosition,Integer refSpeed){
        this.table.put(LocalDateTime.now(),joystickPosition,refSpeed);
    }
    public Table<LocalDateTime, Integer, Integer> getTable(){
    return this.table;
    }
    public int getSize(){
        return this.table.size();
    }
}
