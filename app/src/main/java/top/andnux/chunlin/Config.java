package top.andnux.chunlin;

import top.andnux.utils.storage.annotation.FileName;
import top.andnux.utils.storage.annotation.KeyName;

@FileName("config")
public class Config {
    @KeyName("hhh")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
