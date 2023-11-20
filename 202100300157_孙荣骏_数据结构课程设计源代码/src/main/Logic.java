package main;

import javax.swing.*;
import java.awt.*;

//游戏逻辑
public class Logic
{
	//游戏面板
	private Panel gamePanel;
    

	public Logic(Panel _gamePanel)
	{
		this.gamePanel = _gamePanel;
	}
        
	/**
	 * 设置地雷数与时间提示
	 * 参数(number) : 要显示的数字
	 * 参数(flag) : 标识（0-地雷数，1-时间）
	 */
	public void setNumberTip(int _number,int _flag)
	{
		if(_number <0){_number = 0;}
		
		//将数字转换为3位长字符串，前面不足补零
		String tip = "000" + Integer.toString(_number);
		tip = tip.substring(tip.length() - 3,tip.length());
		if(_flag == 0)      //显示剩余地雷数
		{
			if(_number > this.gamePanel.mineNum){_number = this.gamePanel.mineNum;}
		    for(int i=0;i<3;i++)
		    {
		        this.gamePanel.labelMineTip[i].setIcon(this.gamePanel.imageIconNumberTip[Integer.parseInt(tip.substring(i,i+1))]);
		    }
		}
		else if(_flag == 1)     //显示游戏时间
		{
			if(_number > 999){_number = 999;}
		    for(int i=0;i<3;i++)
		    {
		        this.gamePanel.labelTimeTip[i].setIcon(this.gamePanel.imageIconNumberTip[Integer.parseInt(tip.substring(i,i+1))]);
		    }
		}

	}

	
	/**
	 * 重置所有信息
	 */
	public void resetAll()
	{
		//重置地雷按钮
		for(int row=0;row<this.gamePanel.gridRows;row++)
		{
		    for(int column=0;column<this.gamePanel.gridColumns;column++)
		    {
		    	this.gamePanel.buttonMine[row][column].setIcon(this.gamePanel.imageIconCell);
		    	this.gamePanel.buttonMine[row][column].setPressedIcon(this.gamePanel.imageIconBlank);
		    	this.gamePanel.buttonMine[row][column].setBorder(null);
		    }
		}
		//重置地雷信息
		for(int row=0;row<this.gamePanel.gridRows;row++)
		{
		    for(int column=0;column<this.gamePanel.gridColumns;column++)
		    {
		    	this.gamePanel.mapMine[row][column].put("number",0);	//0个雷
		    	this.gamePanel.mapMine[row][column].put("flag",0);		//未打开
		    }
		}
	}
	
	/**
	 * 随机生成雷并记录周围地雷数
	 */
	public void randomMine()
	{
		//随机生成地雷
		for(int i=0;i<this.gamePanel.mineNum;)
		{
			int row = (int)(Math.random() * this.gamePanel.gridRows);
			int column = (int)(Math.random() * this.gamePanel.gridColumns);
			//判断该位置是否已经有雷
			if(this.gamePanel.mapMine[row][column].get("number") != -1)
			{
				this.gamePanel.mapMine[row][column].put("number",-1);
				i++;
			}
		}
		//记录周围的雷数
		for(int row=0;row<this.gamePanel.gridRows;row++)
		{
		    for(int column=0;column<this.gamePanel.gridColumns;column++)
			{
				if(this.gamePanel.mapMine[row][column].get("number") != -1)
				{
					this.gamePanel.mapMine[row][column].put("number",this.countMineAround(row,column));
				}
			}
		}
	}	
	
	/**
	 * 计算周围的雷数(共八个方位)
	 */
	private int countMineAround(int _row,int _column)
	{
		int count = 0;
		for(int row=_row-1;row<=_row+1;row++)
		{
			if(row < 0 || row >= this.gamePanel.gridRows){continue;}	//行出边界了
			for(int column=_column-1;column<=_column+1;column++)
			{
				if(column < 0 || column >= this.gamePanel.gridColumns){continue;}	//列出边界了
				if(row == _row && column == _column){continue;}	//自身不计算在内
				if(this.gamePanel.mapMine[row][column].get("number") == -1){count++;}
			}
		}
		
		return count;
	}
	
	/**
	 * 显示所有地雷
	 */
	public void showMine()
	{
		for(int row=0;row<this.gamePanel.gridRows;row++)
		{
		    for(int column=0;column<this.gamePanel.gridColumns;column++)
		    {
		    	if(this.gamePanel.mapMine[row][column].get("number") == -1)
		    	{
		    		this.gamePanel.buttonMine[row][column].setIcon(this.gamePanel.imageIconMine);
		    		this.gamePanel.buttonMine[row][column].setPressedIcon(this.gamePanel.imageIconMine);
		    		this.gamePanel.mapMine[row][column].put("flag",1);
		    	}
		    }
		}
	}
	
	/**
	 * 探查单元格以及之后的操作
	 *
	 */
	public void openCell(int _row,int _column)
	{


		//如果状态是已经打开或标注小红旗了就不往下判断了
		if(this.gamePanel.mapMine[_row][_column].get("flag") == 1 || this.gamePanel.mapMine[_row][_column].get("flag") == 2){return;}
		
		//设置该格子的边框为线条形
    	this.gamePanel.buttonMine[_row][_column].setBorder(BorderFactory.createLineBorder(Color.GRAY,1));

		if(this.gamePanel.mapMine[_row][_column].get("number") == -1)			//踩到地雷了
		{
			this.gamePanel.mapMine[_row][_column].put("flag",1);
			this.showMine();	//显示所有雷
    		this.gamePanel.buttonMine[_row][_column].setIcon(this.gamePanel.imageIconBomb);	//本格子的雷特殊标记
    		this.gamePanel.buttonMine[_row][_column].setPressedIcon(this.gamePanel.imageIconBomb);
			this.gamePanel.isGameOver = true;
    		this.gamePanel.isStart = false;
			this.gamePanel.timer.stop();
			return;
		}
		else if(this.gamePanel.mapMine[_row][_column].get("number") == 0)		//踩到空白处
		{
			this.gamePanel.mapMine[_row][_column].put("flag",1);
			this.gamePanel.buttonMine[_row][_column].setIcon(this.gamePanel.imageIconNumber[0]);
			this.gamePanel.buttonMine[_row][_column].setPressedIcon(this.gamePanel.imageIconNumber[0]);
			//开始判断周围四面八方的地雷数
			for(int row=_row-1;row<=_row+1;row++)
			{
				if(row < 0 || row >= this.gamePanel.gridRows){continue;}
				for(int column=_column-1;column<=_column+1;column++)
				{
					if(column < 0 || column >= this.gamePanel.gridColumns){continue;}
					if(row == _row && column == _column){continue;}
					if(this.gamePanel.mapMine[row][column].get("number") != -1){openCell(row,column);}
					//如果是空白需采用递归法继续向该格子的四面八方判断地雷数，直到无空白格
				}
			}
		}
		else	//踩到数字处
		{
			this.gamePanel.mapMine[_row][_column].put("flag",1);
    		this.gamePanel.buttonMine[_row][_column].setIcon(this.gamePanel.imageIconNumber[this.gamePanel.mapMine[_row][_column].get("number")]);
    		this.gamePanel.buttonMine[_row][_column].setPressedIcon(this.gamePanel.imageIconNumber[this.gamePanel.mapMine[_row][_column].get("number")]);
		}
		
		//判断游戏是否结束
		if(this.GameOver())
		{
			this.gamePanel.isGameOver = true;
    		this.gamePanel.isStart = false;
			this.gamePanel.timer.stop();
		}
		
	}


	
	/**
	 * 判断游戏是否结束
	 */
	public boolean GameOver()
	{
		//判断未被打开的方格数与雷数是否相等
		int count = 0;
		for(int row=0;row<this.gamePanel.gridRows;row++)
		{
		    for(int column=0;column<this.gamePanel.gridColumns;column++)
		    {
	    		if(this.gamePanel.mapMine[row][column].get("flag") != 1){count++;}
		    }
		}
		if(count == this.gamePanel.mineNum)
		{
			this.gamePanel.isGameOver = true;
			JOptionPane.showMessageDialog(null,"恭喜您取得了胜利！");
			return true;
		}
		
		return false;
	}
}
