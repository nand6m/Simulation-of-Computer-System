
import java.io.*;
import java.util.Random;

public class CPU
{
	static int PC=0, SP=1000, AC, X, Y, timer=0; //system registers
	static PrintWriter pw = null; //pipe
	static BufferedReader input = null;
	static Process memory_process = null; //child memory process
	static String IR="0";   //system register
	static boolean interrupts=true;
	public static void main(String[] args)
	{
		try
		{
			//MEMORY INITIATION AND CREATE PIPE
			memory_process = Runtime.getRuntime().exec("java Memory "+args[0]);
			input=new BufferedReader(new InputStreamReader(memory_process.getInputStream()));
        	pw = new PrintWriter(new OutputStreamWriter(memory_process.getOutputStream()));

            //INITIATE MEMORY AND CREATE PIPE END

           //EXECUTION STARTS
            while(true)
            {
            	pw.println(PC);   //ask memory for next instruction
        		pw.flush();
            	try{
					IR=input.readLine();
					}  //try to read pipe & store incoming instruction in IR register
            	catch(IOException e)
					{
					System.out.println("\n Error: The user program cannot access system memory");
					System.exit(0);
					}

            		if(IR!=null && !IR.equals(""))
            		{
            			try{
							Integer.parseInt(IR);
							}
                		catch(NumberFormatException e)
						{
							System.out.println("Error:  "+IR);
							memory_process.destroy();
							System.exit(1);
						}
                			switch(Integer.parseInt(IR))
                			{
                			case 1:
                				PC++;
                				pw.println(PC);
                        		pw.flush();
                        		AC=Integer.parseInt(input.readLine());
                        		PC++;
                				break;
                			case 2:
                				PC++;
                				pw.println(PC);
                        		pw.flush();
                        		pw.println(input.readLine());
                        		pw.flush();
                        		AC=Integer.parseInt(input.readLine());
                        		PC++;
                				break;
                			case 3:
                				PC++;
                				pw.println(PC);
                        		pw.flush();
                        		pw.println(input.readLine());
                        		pw.flush();
                        		pw.println(input.readLine());
                        		pw.flush();
                        		AC=Integer.parseInt(input.readLine());
                        		PC++;
                				break;
                			case 4:
                				PC++;
                				pw.println(PC);
                        		pw.flush();
                        		pw.println(Integer.parseInt(input.readLine())+X);
                        		pw.flush();
                        		AC=Integer.parseInt(input.readLine());
                        		PC++;
                				break;
                			case 5:
                				PC++;
                				pw.println(PC);
                        		pw.flush();
                        		pw.println(Integer.parseInt(input.readLine())+Y);
                        		pw.flush();
                        		AC=Integer.parseInt(input.readLine());
                        		PC++;
                				break;
                			case 6:
                				pw.println((SP+X));
                        		pw.flush();
                        		AC=Integer.parseInt(input.readLine());
                        		PC++;
                				break;
                			case 7:
                				PC++;
                				pw.println(PC);
                        		pw.flush();
                        		pw.println(input.readLine()+" "+AC);  //store instructon
                        		pw.flush();
                        		PC++;
                				break;
                			case 8:
                				Random num = new Random();
                				AC=num.nextInt(99)+1; //generate random number from 1 to 100
                				PC++;
                				break;
                			case 9:
                				PC++;
                				pw.println(PC);
                        		pw.flush();
                        		if(Integer.parseInt(input.readLine())==1)
                        			System.out.print(AC);
                        		else
                        			System.out.print((char)AC);
                        		PC++;
                				break;
                			case 10:
                				AC=AC+X;
                				PC++;
                				break;
                			case 11:
                				AC=AC+Y;
                				PC++;
                				break;
                			case 12:
                				AC=AC-X;
                				PC++;
                				break;
                			case 13:
                				AC=AC-Y;
                				PC++;
                				break;
                			case 14:
                				X=AC;
                				PC++;
                				break;
                			case 15:
                				AC=X;
                				PC++;
                				break;
                			case 16:
                				Y=AC;
                				PC++;
                				break;
                			case 17:
                				AC=Y;
                				PC++;
                				break;
                			case 18:
                				SP=AC;
                				PC++;
                				break;
                			case 19:
                				AC=SP;
                				PC++;
                				break;
                			case 20:
                				PC++;
                				pw.println(PC);
                        		pw.flush();
                        		PC=Integer.parseInt(input.readLine());
                				break;
                			case 21:
                				PC++;
                				if(AC==0)
                				{
                					pw.println(PC);
                            		pw.flush();
                            		PC=Integer.parseInt(input.readLine());
                				}
                				else{PC++;}
                				break;
                			case 22:
                				PC++;
                				if(AC!=0)
                				{
                					pw.println(PC);
                            		pw.flush();
                            		PC=Integer.parseInt(input.readLine());
                				}
                				else{PC++;}
                				break;
                			case 23:
                				PC++;
                        		if(SP>500)    //check if stack is full to ensure it does not intersect with the user program
                        		{
                        			SP--;
                            		pw.println(SP+" "+(++PC));
                            		pw.flush();
                            		pw.println(--PC);
                            		pw.flush();
                            		PC=Integer.parseInt(input.readLine());
                        		}
                        		else{
					System.out.println("Error: User Stack Full.");
					memory_process.destroy();System.exit(1);
					}
                   				break;
                			case 24:
                				pw.println(SP);
                            	pw.flush();
                            	PC=Integer.parseInt(input.readLine());
                            	SP++;
                				break;
                			case 25:
                				X++;
                				PC++;
                				break;
                			case 26:
                				X--;
                				PC++;
                				break;
                			case 27:
                				if(SP>500) // //check if stack is full to ensure it does not intersect with the user program
                        		{
                        			SP--;
                            		pw.println(SP+" "+AC);   //store AC
                            		pw.flush();
                            		PC++;
                        		}
                        		else{System.out.println("Error: User Stack Full");
					memory_process.destroy();
					System.exit(1);
					}
                				break;
                			case 28:
                				pw.println(SP);
                				pw.flush();
                            	AC=Integer.parseInt(input.readLine());
                            	SP++;
                            	PC++;
                				break;
                			case 29:
                				if(interrupts==true)  //logic for disabling nested interrupt processing
                				{
                					//store system state before switching space
                					pw.println("1999 "+SP);
                    				pw.println("1998 "+(++PC));
                    				pw.println("1997 "+AC);
                    				pw.println("1996 "+X);
                    				pw.println("1995 "+Y);
                    				PC=1500;
                    				SP=1995;
                    				interrupts=false;
						}
                				else{PC++;}
                				break;
                			case 30:
                				interrupts=true;
                				if(SP<2000)
                				{
                					//System state being restored
                					pw.println(SP);
                            		pw.flush();
                            		Y=Integer.parseInt(input.readLine());
                            		SP++;
                            		pw.println(SP);
                            		pw.flush();
                            		X=Integer.parseInt(input.readLine());
                            		SP++;
                            		pw.println(SP);
                            		pw.flush();
                            		AC=Integer.parseInt(input.readLine());
                            		SP++;
                					pw.println(SP);
                            		pw.flush();
                            		PC=Integer.parseInt(input.readLine());
                            		SP++;
                            		pw.println(SP);
                            		pw.flush();
                            		SP=Integer.parseInt(input.readLine());
                				}
                				else{System.out.println("Error: System Stack Empty");
								memory_process.destroy();
								System.exit(1);}
                				break;
                			case 50:
                				pw.println("end");
                				System.exit(0);

                			}
            		}
            		// TIMER LOGIC STARTS
            		timer++;
            		if(timer==Integer.parseInt(args[1]))
            		{
            			if(interrupts==true) //check if in user mode
        				{
        					pw.println("1999 "+SP);
            				pw.println("1998 "+(PC));
            				pw.println("1997 "+AC);
            				pw.println("1996 "+X);
            				pw.println("1995 "+Y);
            				PC=1000;
            				SP=1995;
            				interrupts=false;
        				}
        				timer=0;
            		}
            		//TIMER LOGIC ENDS
            }
            //EXECUTION ENDS
		}
		catch(Exception e){System.exit(0);}
	}
}
