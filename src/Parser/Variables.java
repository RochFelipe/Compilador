package Parser;

import java.util.Objects;

public class Variables {

    public static final int VAR_INT = 1;
    public static final int VAR_CHAR = 2;
    public static final int VAR_FLOAT = 3;

    private String name;
    private String type;
    private int typeID;
    private int bloco;

    public Variables() {
         super();
    }

    public Variables(int type, String name, int bloco) {
        super();
        
        this.name = name;
        this.bloco = bloco;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTypeID() {
        if (typeID == 1) {
            return VAR_INT;
        } else if (typeID == 2 ) {
            return VAR_CHAR;
        } else {
            return VAR_FLOAT;
        }
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public int getBloco() {
        return bloco;
    }

    public void setBloco(int bloco) {
        this.bloco = bloco;
    }

    @Override
    public String toString() {
        return "Variable{" + "name=" + name + ", type=" + type + ", bloco=" + bloco + '}';
    }

    @Override
    public boolean equals(Object obj) {
        final Variables other = (Variables) obj;
        if (this.bloco != other.bloco) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

}
