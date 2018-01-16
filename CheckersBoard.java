package com.example.kamran.multiplayergame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CheckersBoard extends AppCompatActivity {
    int goatType1=0;
    int goatType2=1;
    String goatType1Color="Black";
    String goatType2Color="White";
    boolean selected=false,goat_select=true,goat_unselect=false,king_jump=false;
    Blocks current_block, selected_block;
    View currentIV,selectedIV;//imageViews to use globally
    Coordinates currentBlock,selectedBlockCoordinates;
    Coordinates die_goat;
    int counter_x,counter_y;
    int counter_king_hurdles=0;
    Blocks[][] blocks_state;

    public class Blocks
    {
        int goat_value;
        String goat_color;
        boolean king;
        boolean fill;
        public Blocks()
        {

            goat_value=-10;
            goat_color="red";
            king=false;
            fill=false;
        }
        public Blocks(int v, String c,boolean k)
        {
            goat_value=v;
            goat_color=c;
            king=k;
            fill=true;
        }
        public void update(int v, String c,boolean k)
        {
            goat_value=v;
            goat_color=c;
            king=k;
            fill=true;
        }


    }
    public class Coordinates
    {
        int x;
        int y;
        public Coordinates(int px,int py)
        {
            x=px;
            y=py;

        }
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkers_board);
        // add necessary intent values to be matched.
        //Blocks blocks_state= new Blocks(0,"Black",false);
        blocks_state= new Blocks[8][4];
        for (int row=0;row<8;row++)
            for (int column=0;column<4;column++)
            {
                if (row<3)
                {

                    blocks_state[row][column]= new Blocks(goatType1,goatType1Color,false);
                }
                else if (row>4)
                {
                    blocks_state[row][column]= new Blocks(goatType2,goatType2Color,false);
                }
                else
                {
                    blocks_state[row][column]=new Blocks();
                }
            }

    }//End of On Create



    public void moveGoat(Coordinates currentParam, Coordinates selectedParam,int value,View currentImageView,View selectedImageView)
    {
        boolean kingTemp;
        String colorTemp;

        blocks_state[currentParam.x][currentParam.y]=new Blocks();
        if (value==0)
        {
            colorTemp="Black";
            if (selectedParam.x==7)
            {
                kingTemp=true;
            }
            else {
                kingTemp=current_block.king;
            }

        }
        else
        {
            colorTemp="White";
            if (selectedParam.x==0)
            {
                kingTemp=true;
            }
            else {
                kingTemp=current_block.king;
            }

        }
        blocks_state[selectedParam.x][selectedParam.y].update(value,colorTemp,kingTemp);
        // Any API
        currentImageView.setBackgroundResource(0);
        currentImageView.setBackgroundColor(0);

        if (value==0) {
            if(kingTemp==true)
            {
                selectedImageView.setBackgroundResource(R.drawable.king_black);
            }
            else {

                selectedImageView.setBackgroundResource(R.drawable.goat_one);
            }


        }
        else
        {
            if(kingTemp==true)
            {
                selectedImageView.setBackgroundResource(R.drawable.king_white);
            }
            else {

                selectedImageView.setBackgroundResource(R.drawable.goat_two);
            }
        }
        current_block=new Blocks();
        selected_block=new Blocks();
        currentBlock=new Coordinates(-10,-10);
        selectedBlockCoordinates= new Coordinates(-10,-10);
    }//End of Move goat

    public void squareOnClick(View view)
    {

        String idName = getResources().getResourceEntryName(view.getId());
        idName = idName.replace("square_", "");

        String[] coordinate = idName.split("_");
        if(coordinate.length != 2)
            return;

        int x = Integer.parseInt(coordinate[0])-1;
        int y = Integer.parseInt(coordinate[1])-1;
        Log.i("Activity_Checker", "Coordinate pressed: " + x + ":" + y);


        if (selected==false)
        {


            currentBlock = new Coordinates(x,y);//store current block coordinates
            current_block  = blocks_state[x][y];
            if (current_block.fill==false)
            {
                showToast("Select the goat to move first.");


            }
            else
            {
                selected = true;
                currentIV=view;
                TextView tv = (TextView) findViewById(R.id.statustextview);
                tv.setText("current:"+" "+"X:" + x + " Y:" + y+" " +blocks_state[x][y].goat_color+String.valueOf(blocks_state[x][y].fill));
                if (blocks_state[x][y].goat_value==0) {
                    if(blocks_state[x][y].king==true)
                    {
                        currentIV.setBackgroundResource(R.drawable.king_selected_black);
                    }
                    else {
                        currentIV.setBackgroundResource(R.drawable.selected_black);
                    }
                }
                else {
                    if(blocks_state[x][y].king==true)
                    {
                        currentIV.setBackgroundResource(R.drawable.king_selected_white);
                    }
                    else {
                        currentIV.setBackgroundResource(R.drawable.selected_white);
                    }
                }
                showToast("Please select the block to move the goat.");
            }
        }
        else if (selected==true) {
            selectedBlockCoordinates = new Coordinates(x, y);
            selectedIV = view;
            TextView tv1 = (TextView) findViewById(R.id.selectedBlocktextview);
            tv1.setText("Selected"+""+"X:" + selectedBlockCoordinates.x+ " Y:" + selectedBlockCoordinates.y+" " +blocks_state[x][y].goat_color+String.valueOf(blocks_state[x][y].fill));

            if (selectedBlockCoordinates.x!=currentBlock.x||selectedBlockCoordinates.y!=currentBlock.y) {
                selected_block = blocks_state[x][y];

                if (selected_block.fill == true) {
                    showToast("Wrong Selection Filled Already");
                    // selected=false;
                } else {
                    if ((selectedBlockCoordinates.x == ((currentBlock.x) - 1) || selectedBlockCoordinates.x == ((currentBlock.x) + 1))
                            &&
                            (selectedBlockCoordinates.y == ((currentBlock.y)) || selectedBlockCoordinates.y == ((currentBlock.y) + 1) || (selectedBlockCoordinates.y == ((currentBlock.y) - 1)))
                            ) {
                        if (current_block.goat_value == 0) {

                            if (selectedBlockCoordinates.x > currentBlock.x||current_block.king==true) {
                                //move goat here
                                moveGoat(currentBlock, selectedBlockCoordinates, 0, currentIV, selectedIV);
                                selected = false;
                            } else {
                                showToast("Invalid move black");
                                //selected=false;
                            }
                        } else {
                            if (selectedBlockCoordinates.x < currentBlock.x||current_block.king==true) {
                                //move goat here
                                moveGoat(currentBlock, selectedBlockCoordinates, 1, currentIV, selectedIV);
                                selected = false;
                            } else {
                                showToast("Invalid move white");

                                // selected=false;
                            }
                        }
                    } else if ((selectedBlockCoordinates.x == ((currentBlock.x) - 2) || selectedBlockCoordinates.x == ((currentBlock.x) + 2))
                            &&
                            ((selectedBlockCoordinates.y == ((currentBlock.y) + 1)) || (selectedBlockCoordinates.y == ((currentBlock.y) - 1)))
                            ) {
                        //goat die condition
                        if (blocks_state[selectedBlockCoordinates.x][selectedBlockCoordinates.y].fill == false) {
                            //if the selected is not filled then come to check if the goat can die

                            if (currentBlock.y > selectedBlockCoordinates.y) {
                                if (currentBlock.x > selectedBlockCoordinates.x) {
                                    //this is the condition to die goat in upper left
                                    if (currentBlock.x % 2 == 0) {
                                        die_goat = new Coordinates(currentBlock.x - 1, currentBlock.y - 1);
                                    } else {
                                        die_goat = new Coordinates(currentBlock.x - 1, currentBlock.y);

                                    }

                                } else {
                                    //this is the condition to die goat in lower left
                                    if (currentBlock.x % 2 == 0) {
                                        die_goat = new Coordinates(currentBlock.x + 1, currentBlock.y - 1);
                                    } else {
                                        die_goat = new Coordinates(currentBlock.x + 1, currentBlock.y);

                                    }
                                }

                            } else if (currentBlock.y < selectedBlockCoordinates.y) {
                                if (currentBlock.x > selectedBlockCoordinates.x) {

                                    if (currentBlock.x % 2 == 0) {
                                        die_goat = new Coordinates(currentBlock.x - 1, currentBlock.y);
                                    } else {
                                        die_goat = new Coordinates(currentBlock.x - 1, currentBlock.y + 1);

                                    }


                                } else {
                                    if (currentBlock.x % 2 == 0) {
                                        die_goat = new Coordinates(currentBlock.x + 1, currentBlock.y);
                                    } else {
                                        die_goat = new Coordinates(currentBlock.x + 1, currentBlock.y + 1);

                                    }

                                }
                            }

                            if (blocks_state[die_goat.x][die_goat.y].fill == true) {


                                if ((blocks_state[die_goat.x][die_goat.y].goat_value == 0 && current_block.goat_value == 1) || (blocks_state[die_goat.x][die_goat.y].goat_value == 1 && current_block.goat_value == 0)) {
                                    String packageName = getPackageName();
                                    String tempCurrent = "square_" + String.valueOf(die_goat.x + 1) + "_" + String.valueOf(die_goat.y + 1);
                                    int resId = getResources().getIdentifier(tempCurrent, "id", packageName);
                                    ImageView imgView = (ImageView) findViewById(resId);
                                    imgView.setBackgroundResource(0);
                                    TextView tv2 = (TextView) findViewById(R.id.aftertextView);
                                    tv2.setText("die goat:" + " " + "X:" + die_goat.x + " Y:" + die_goat.y + " " + blocks_state[die_goat.x][die_goat.y].goat_color + String.valueOf(blocks_state[die_goat.x][die_goat.y].fill));

                                    blocks_state[die_goat.x][die_goat.y] = new Blocks();
                                    moveGoat(currentBlock, selectedBlockCoordinates, current_block.goat_value, currentIV, selectedIV);
                                    selected = false;

                                } else {
                                    //same goats in diagnol cant die
                                    showToast("Invalid Move.");
                                }

                            } else {
                                //invalid because the goat to die is empty
                                showToast("Invalid Move,");
                            }

                        } else {
                            showToast("Invalid Move/");
                        }
                    } else if (Math.abs(currentBlock.x - selectedBlockCoordinates.x) > 2) {
                        if (current_block.king == true) {
                            TextView tv3 = (TextView) findViewById(R.id.textView2);

                            //goat die condition
                            if (blocks_state[selectedBlockCoordinates.x][selectedBlockCoordinates.y].fill == false) {
                                //if the selected is not filled then come to check if the goat can die

                                if (currentBlock.y > selectedBlockCoordinates.y) {
                                    if (currentBlock.x > selectedBlockCoordinates.x) {
                                        //this is the condition to die goat in upper left
                                        if (currentBlock.x % 2 == 0) {
                                            Log.d("King","Upperleft through even");
                                            counter_x=currentBlock.x;
                                            counter_y=currentBlock.y;

                                        }
                                        else
                                        {
                                            counter_x=currentBlock.x-1;
                                            counter_y=currentBlock.y;
                                            tv3.setText("counter x y :" + " " + "X:" + counter_x + " Y:" + counter_y + " " + blocks_state[counter_x][counter_y].goat_color + String.valueOf(blocks_state[counter_x][counter_y].fill));

                                            Log.d("kingoddupperleft",String.valueOf(counter_x)+ String.valueOf(counter_y));
                                            if (blocks_state[counter_x][counter_y].fill == true) {
                                                king_jump = true;
                                                counter_king_hurdles=counter_king_hurdles+1;
                                                die_goat = new Coordinates(counter_x, counter_y);
                                                tv3.setText("counter x y :" + " " + "X:" + counter_x + " Y:" + counter_y + " " + blocks_state[counter_x][counter_y].goat_color + String.valueOf(blocks_state[counter_x][counter_y].fill));
                                                Log.d("inoddfirstloop",String.valueOf(counter_x)+ String.valueOf(counter_y));

                                            }


                                        }
                                        while (counter_x>selectedBlockCoordinates.x) {
                                            counter_x=counter_x-1;
                                            counter_y=counter_y-1;
                                            Log.d("inwhile",String.valueOf(counter_x)+","+ String.valueOf(counter_y));
                                            Log.d("inwhilefill ",String.valueOf(blocks_state[counter_x][counter_y].fill));

                                            if (blocks_state[counter_x][counter_y].fill == true) {
                                                king_jump = true;
                                                counter_king_hurdles=counter_king_hurdles+1;
                                                die_goat = new Coordinates(counter_x, counter_y);
                                                tv3.setText("counter x y :" + " " + "X:" + counter_x + " Y:" + counter_y + " " + blocks_state[counter_x][counter_y].goat_color + String.valueOf(blocks_state[counter_x][counter_y].fill));
                                                Log.d("incondition1",String.valueOf(counter_x)+","+ String.valueOf(counter_y));

                                                //     break;
                                            }
                                            counter_x = counter_x - 1;
                                            Log.d("inwhilefill ",String.valueOf(blocks_state[counter_x][counter_y].fill));

                                            if( counter_x >selectedBlockCoordinates.x )
                                            {
                                                if (blocks_state[counter_x][counter_y].fill == true) {
                                                    king_jump = true;
                                                    counter_king_hurdles = counter_king_hurdles + 1;
                                                    die_goat = new Coordinates(counter_x, counter_y);
                                                    Log.d("incondition2",String.valueOf(counter_x)+","+ String.valueOf(counter_y));

                                                    tv3.setText("counter x y :" + " " + "X:" + counter_x + " Y:" + counter_y + " " + blocks_state[counter_x][counter_y].goat_color + String.valueOf(blocks_state[counter_x][counter_y].fill));
                                                    //.break;
                                                }
                                                Log.d("Messagecountersxx", String.valueOf(counter_x) + String.valueOf(counter_y));

                                            }
                                        }
                                        //end of if condition of die_goat king upper left

                                    } else {
                                        //this is the condition to die goat in lower left


                                        if (currentBlock.x % 2 == 0) {
                                            Log.d("King","lowerleft through even");
                                            counter_x=currentBlock.x;
                                            counter_y=currentBlock.y;

                                        }
                                        else
                                        {
                                            counter_x=currentBlock.x+1;
                                            counter_y=currentBlock.y;
                                            tv3.setText("counter x y :" + " " + "X:" + counter_x + " Y:" + counter_y + " " + blocks_state[counter_x][counter_y].goat_color + String.valueOf(blocks_state[counter_x][counter_y].fill));

                                            Log.d("kingoddlowerleft",String.valueOf(counter_x)+ String.valueOf(counter_y));
                                            if (blocks_state[counter_x][counter_y].fill == true) {
                                                king_jump = true;
                                                counter_king_hurdles=counter_king_hurdles+1;
                                                die_goat = new Coordinates(counter_x, counter_y);
                                                tv3.setText("counter x y :" + " " + "X:" + counter_x + " Y:" + counter_y + " " + blocks_state[counter_x][counter_y].goat_color + String.valueOf(blocks_state[counter_x][counter_y].fill));
                                                Log.d("inoddfirstloop",String.valueOf(counter_x)+ String.valueOf(counter_y));

                                            }


                                        }
                                        while (counter_x<selectedBlockCoordinates.x) {
                                            counter_x=counter_x+1;
                                            counter_y=counter_y-1;
                                            Log.d("inwhile",String.valueOf(counter_x)+","+ String.valueOf(counter_y));
                                            Log.d("inwhilefill ",String.valueOf(blocks_state[counter_x][counter_y].fill));

                                            if (blocks_state[counter_x][counter_y].fill == true) {
                                                king_jump = true;
                                                counter_king_hurdles=counter_king_hurdles+1;
                                                die_goat = new Coordinates(counter_x, counter_y);
                                                tv3.setText("counter x y :" + " " + "X:" + counter_x + " Y:" + counter_y + " " + blocks_state[counter_x][counter_y].goat_color + String.valueOf(blocks_state[counter_x][counter_y].fill));
                                                Log.d("incondition1",String.valueOf(counter_x)+","+ String.valueOf(counter_y));

                                                //     break;
                                            }
                                            counter_x = counter_x + 1;
                                            //Log.d("inwhilefill ",String.valueOf(blocks_state[counter_x][counter_y].fill));

                                            if( counter_x<selectedBlockCoordinates.x )
                                            {
                                                if (blocks_state[counter_x][counter_y].fill == true) {
                                                    king_jump = true;
                                                    counter_king_hurdles = counter_king_hurdles + 1;
                                                    die_goat = new Coordinates(counter_x, counter_y);
                                                    Log.d("incondition2",String.valueOf(counter_x)+","+ String.valueOf(counter_y));

                                                    tv3.setText("counter x y :" + " " + "X:" + counter_x + " Y:" + counter_y + " " + blocks_state[counter_x][counter_y].goat_color + String.valueOf(blocks_state[counter_x][counter_y].fill));
                                                    //.break;
                                                }
                                                Log.d("Messagecountersxx", String.valueOf(counter_x) + String.valueOf(counter_y));

                                            }
                                        }
                                    }


                                }
                                // end if left side
                                else if (currentBlock.y < selectedBlockCoordinates.y) {
                                    if (currentBlock.x > selectedBlockCoordinates.x) {
                                        //upper right king die move
                                        if (currentBlock.x % 2 != 0) {
                                            Log.d("King","Upper right  through odd");
                                            counter_x=currentBlock.x;
                                            counter_y=currentBlock.y;

                                        }
                                        else
                                        {
                                            counter_x=currentBlock.x-1;
                                            counter_y=currentBlock.y;
                                            tv3.setText("counter x y :" + " " + "X:" + counter_x + " Y:" + counter_y + " " + blocks_state[counter_x][counter_y].goat_color + String.valueOf(blocks_state[counter_x][counter_y].fill));

                                            Log.d("kingoddupperright",String.valueOf(counter_x)+ String.valueOf(counter_y));
                                            if (blocks_state[counter_x][counter_y].fill == true) {
                                                king_jump = true;
                                                counter_king_hurdles=counter_king_hurdles+1;
                                                die_goat = new Coordinates(counter_x, counter_y);
                                                tv3.setText("counter x y :" + " " + "X:" + counter_x + " Y:" + counter_y + " " + blocks_state[counter_x][counter_y].goat_color + String.valueOf(blocks_state[counter_x][counter_y].fill));
                                                Log.d("inevenfirstloop",String.valueOf(counter_x)+ String.valueOf(counter_y));

                                            }


                                        }
                                        while (counter_x>selectedBlockCoordinates.x) {
                                            counter_x=counter_x-1;
                                            counter_y=counter_y+1;
                                            Log.d("inwhile",String.valueOf(counter_x)+","+ String.valueOf(counter_y));
                                            Log.d("inwhilefill ",String.valueOf(blocks_state[counter_x][counter_y].fill));

                                            if (blocks_state[counter_x][counter_y].fill == true) {
                                                king_jump = true;
                                                counter_king_hurdles=counter_king_hurdles+1;
                                                die_goat = new Coordinates(counter_x, counter_y);
                                                tv3.setText("counter x y :" + " " + "X:" + counter_x + " Y:" + counter_y + " " + blocks_state[counter_x][counter_y].goat_color + String.valueOf(blocks_state[counter_x][counter_y].fill));
                                                Log.d("incondition1",String.valueOf(counter_x)+","+ String.valueOf(counter_y));

                                                //     2break;
                                            }
                                            counter_x = counter_x - 1;
                                            Log.d("inwhilefill ",String.valueOf(blocks_state[counter_x][counter_y].fill));

                                            if( counter_x >selectedBlockCoordinates.x )
                                            {
                                                if (blocks_state[counter_x][counter_y].fill == true) {
                                                    king_jump = true;
                                                    counter_king_hurdles = counter_king_hurdles + 1;
                                                    die_goat = new Coordinates(counter_x, counter_y);
                                                    Log.d("incondition2",String.valueOf(counter_x)+","+ String.valueOf(counter_y));

                                                    tv3.setText("counter x y :" + " " + "X:" + counter_x + " Y:" + counter_y + " " + blocks_state[counter_x][counter_y].goat_color + String.valueOf(blocks_state[counter_x][counter_y].fill));
                                                    //.break;
                                                }
                                                Log.d("Messagecountersxx", String.valueOf(counter_x) + String.valueOf(counter_y));

                                            }
                                        }

                                    } else {
                                        //lower right move king
                                        if (currentBlock.x % 2 != 0) {
                                            Log.d("King","Upperleft through odd");
                                            counter_x=currentBlock.x;
                                            counter_y=currentBlock.y;

                                        }
                                        else
                                        {
                                            counter_x=currentBlock.x+1;
                                            counter_y=currentBlock.y;
                                            tv3.setText("counter x y :" + " " + "X:" + counter_x + " Y:" + counter_y + " " + blocks_state[counter_x][counter_y].goat_color + String.valueOf(blocks_state[counter_x][counter_y].fill));

                                            Log.d("kingevenupperleft",String.valueOf(counter_x)+ String.valueOf(counter_y));
                                            if (blocks_state[counter_x][counter_y].fill == true) {
                                                king_jump = true;
                                                counter_king_hurdles=counter_king_hurdles+1;
                                                die_goat = new Coordinates(counter_x, counter_y);
                                                tv3.setText("counter x y :" + " " + "X:" + counter_x + " Y:" + counter_y + " " + blocks_state[counter_x][counter_y].goat_color + String.valueOf(blocks_state[counter_x][counter_y].fill));
                                                Log.d("inevenfirstloop",String.valueOf(counter_x)+ String.valueOf(counter_y));

                                            }


                                        }
                                        while (counter_x<selectedBlockCoordinates.x) {
                                            counter_x=counter_x+1;
                                            counter_y=counter_y+1;
                                            Log.d("inwhile",String.valueOf(counter_x)+","+ String.valueOf(counter_y));
                                            Log.d("inwhilefill ",String.valueOf(blocks_state[counter_x][counter_y].fill));

                                            if (
                                                    blocks_state[counter_x][counter_y].fill == true) {
                                                king_jump = true;
                                                counter_king_hurdles=counter_king_hurdles+1;
                                                die_goat = new Coordinates(counter_x, counter_y);
                                                tv3.setText("counter x y :" + " " + "X:" + counter_x + " Y:" + counter_y + " " + blocks_state[counter_x][counter_y].goat_color + String.valueOf(blocks_state[counter_x][counter_y].fill));
                                                Log.d("incondition1",String.valueOf(counter_x)+","+ String.valueOf(counter_y));

                                                //     break;
                                            }
                                            counter_x = counter_x + 1;
                                            Log.d("inwhilefill ",String.valueOf(blocks_state[counter_x][counter_y].fill));

                                            if( counter_x <selectedBlockCoordinates.x )
                                            {
                                                if (blocks_state[counter_x][counter_y].fill == true) {
                                                    king_jump = true;
                                                    counter_king_hurdles = counter_king_hurdles + 1;
                                                    die_goat = new Coordinates(counter_x, counter_y);
                                                    Log.d("incondition2",String.valueOf(counter_x)+","+ String.valueOf(counter_y));

                                                    tv3.setText("counter x y :" + " " + "X:" + counter_x + " Y:" + counter_y + " " + blocks_state[counter_x][counter_y].goat_color + String.valueOf(blocks_state[counter_x][counter_y].fill));
                                                    //.break;
                                                }
                                                Log.d("Messagecountersxx", String.valueOf(counter_x) + String.valueOf(counter_y));

                                            }
                                        }
                                    }
                                }

                                if (king_jump == true&&counter_king_hurdles==1) {


                                    if ((blocks_state[die_goat.x][die_goat.y].goat_value == 0 && current_block.goat_value == 1) || (blocks_state[die_goat.x][die_goat.y].goat_value == 1 && current_block.goat_value == 0)) {
                                        String packageName = getPackageName();
                                        String tempCurrent = "square_" + String.valueOf(die_goat.x + 1) + "_" + String.valueOf(die_goat.y + 1);
                                        int resId = getResources().getIdentifier(tempCurrent, "id", packageName);
                                        ImageView imgView = (ImageView) findViewById(resId);
                                        imgView.setBackgroundResource(0);
                                        TextView tv2 = (TextView) findViewById(R.id.aftertextView);
                                        tv2.setText("die goat:" + " " + "X:" + die_goat.x + " Y:" + die_goat.y + " " + blocks_state[die_goat.x][die_goat.y].goat_color + String.valueOf(blocks_state[die_goat.x][die_goat.y].fill));

                                        blocks_state[die_goat.x][die_goat.y] = new Blocks();
                                        moveGoat(currentBlock, selectedBlockCoordinates, current_block.goat_value, currentIV, selectedIV);
                                        selected = false;
                                        king_jump=false;
                                        counter_king_hurdles=0;

                                    } else {
                                        //same goats in diagnol cant die
                                        showToast("Invalid Move.");
                                    }

                                } else {
                                    //invalid because the goat to die is empty
                                    showToast("Invalid Move,");
                                }

                            } else {
                                showToast("Invalid Move/");
                            }
                            //end of king else condiiton


                        } else {
                            showToast("Invalid Move");
                        }
                    }

                }




            }
            else {
               /* showToast(
                        "Select any other block."
                );*/

                //unselect the selected
                if (blocks_state[currentBlock.x][currentBlock.y].goat_value==0) {
                    if(blocks_state[x][y].king==true)
                    {
                        view.setBackgroundResource(R.drawable.king_black);
                    }
                    else {

                        view.setBackgroundResource(R.drawable.goat_one);
                    }

                }
                else {
                    if(blocks_state[x][y].king==true)
                    {
                        view.setBackgroundResource(R.drawable.king_white);
                    }
                    else {

                        view.setBackgroundResource(R.drawable.goat_two);
                    }

                }
                selected=false;


                current_block=new Blocks();
                selected_block=new Blocks();
                currentBlock=new Coordinates(-10,-10);
                selectedBlockCoordinates= new Coordinates(-10,-10);

            }

        }


    }

    public void showToast(String parameterMessage)
    {
        Toast.makeText(CheckersBoard.this,parameterMessage,Toast.LENGTH_LONG).show();

    }


}


