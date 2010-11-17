package com.ogame.terminal.domain;

public class Building {

	/*
		<a class="fastBuild tipsStandard" title="|Dezvolta Mina de Metal la nivelul 1" 
			href="http://uni3.ogame.ro/game/index.php?page=resources&amp;session=5fb19d0edf17&amp;cp=33880740#" 
			onclick="sendBuildRequest('index.php?page=resources&amp;session=5fb19d0edf17&amp;modus=1&amp;type=1&amp;menge=1&amp;token=2231a28f2be6332abe398bab0aa00a20');">
			<img src="./resurse2_files/pixel.gif" width="22" height="14">
		</a>
	 */
	
	private String name;
	private int level;
	
	public Building() {}
	public Building(String name, int level) {
		super();
		this.name = name;
		this.level = level;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	@Override
	public String toString() {
		return "Building [name=" + name + ", level=" + level + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Building other = (Building) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
