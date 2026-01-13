#include <stdio.h>
#include <string>
#include <iostream>
//#include <gtkmm.h>














const std::string horizontalLine = "\u2500"; // Horizontal line ─
const std::string topLeftCorner = "\u250C";  // Top-left corner ┌
const std::string topRightCorner = "\u2510"; // Top-right corner ┐
const std::string bottomLeftCorner = "\u2514"; // Bottom-left corner └
const std::string bottomRightCorner = "\u2518"; // Bottom-right corner ┘



void getTerminalSize(int& rows, int& cols) {
    struct winsize ws;
    if (ioctl(STDOUT_FILENO, TIOCGWINSZ, &ws) == 0) {
        rows = ws.ws_row;
        cols = ws.ws_col;
    } else {
        rows = 0;
        cols = 0;
    }
}

void setColor(const std::string& color) {
    if (color == "purple") {
        std::cout << "\033[48;5;21m"; // Set background color to purple
    } else if (color == "lightgrey") {
        std::cout << "\033[48;5;254m"; // Set background color to light grey
    }
}

void setCursorPosition(int x, int y) {
    std::cout << "\033[" << y << ";" << x << "H";
}

void resetColor() {
    std::cout << "\033[0m"; // Reset to default color
}

void PrintUsage() {
	std::vector<std::string> infoText;
  	infoText.push_back("Usage:");
	infoText.push_back("-soundOff         | Disables the sound that plays. Does not disable the TTS");
  	infoText.push_back("-noTTS            | Disables the TTS. Use this if you don't have festival installed or are annoyed by the voices used");
  	infoText.push_back("-amount <int>     | Specifies the amount of sentences the LM generates");
  	infoText.push_back("-filter           | Disables the filter. The filter is used only to filter out a choice selection of words. Use at your own risk.");
  	int rows, cols;
    	getTerminalSize(rows, cols);
    	if (cols < 130 || rows < 30) {
    		return;
    	}
    	setCursorPosition(0, 0);
    	setColor("lightgrey");
    	for (int i = 0; i < 7; ++i) {
        	setCursorPosition(0, i);
        	std::cout << std::string(132, ' ') << '\n';
    	}
    	//draw the shadow
    	std::cout << "\033[40m";
    	//bottom
   	setCursorPosition(2, 7);
   	std::cout << std::string(131, ' ') << '\n';
    	//side
    	setCursorPosition(133, 1);
    	for (int i = 2; i < 8; i++) {
        	setCursorPosition(133, i);
       	 	std::cout << " " << '\n';
    	}
    	//draw fansy border stuff
    	setCursorPosition(0, 0);
    	setColor("lightgrey");
    	std::cout << "\033[30m";
    	//left side│
    	//│
    	for (int i = 1; i < 7 - 1; i++) {
    	    setCursorPosition(0, 0 + i);
    	    std::cout << "│" << '\n';
    	}
    	//right side
    	for (int i = 1; i < 7 - 1; i++) {
    	    setCursorPosition(0 + 132, 0 + i);
    	    std::cout << "│" << '\n';
    	}
    	//bottom└┘
    	setCursorPosition(0, 0 + 7 - 1);
    	std::cout << "└";
    	for (int i = 0; i < 132 -2; i++) std::cout << "─";
    	std::cout << "┘" << '\n';
    	//top┌┐
    	setCursorPosition(0, 0);
    	std::cout << "┌";
    	for (int i = 0; i < 132 -2; i++) std::cout << "─";
    	std::cout << "┐" <<'\n'; 
    	
    	//text stuff
    	setCursorPosition(3, 0);
    	std::cout << infoText[0] << '\n';
    	setCursorPosition(2, 2);
    	std::cout << infoText[1] << '\n';
    	setCursorPosition(2, 3);
    	std::cout << infoText[2] << '\n';
    	setCursorPosition(2, 4);
    	std::cout << infoText[3] << '\n';
    	setCursorPosition(2, 5);
    	std::cout << infoText[4] << '\n';
}

void printInterface(const std::vector<std::string>& text, const float& wWP, const float& wHP, const int& a1, const int& a2) {
    int rows, cols;
    getTerminalSize(rows, cols);
    int windowWidth = cols * wWP;
    int windowHeight = rows * wHP;
    int windowX = (cols - windowWidth) / 2;
    int windowY = (rows - windowHeight) / 2;
    setColor("purple");
    std::cout << std::string(cols, ' ') << std::endl; // Top border
    for (int i = 0; i < rows; ++i) {
        std::cout << std::string(cols, ' ') << '\n';
    }
    
    //draw the window
    setColor("lightgrey");
    for (int i = 0; i < windowHeight; ++i) {
        setCursorPosition(windowX, windowY + i);
        std::cout << std::string(windowWidth, ' ') << '\n';
    }
    //draw the shadow
    std::cout << "\033[40m";
    //bottom
    setCursorPosition(windowX + 2, windowY + windowHeight);
    std::cout << std::string(windowWidth, ' ') << '\n';
    //side
    setCursorPosition(windowX + windowWidth, windowY);
    for (int i = 1; i < windowHeight+1; i++) {
        setCursorPosition(windowX + windowWidth, windowY + i);
        std::cout << std::string(2, ' ') << '\n';
    }
    //draw fansy border stuff
    setCursorPosition(windowX, windowY);
    setColor("lightgrey");
    std::cout << "\033[30m";
    //left side│
    //│
    for (int i = 1; i < windowHeight - 1; i++) {
        setCursorPosition(windowX, windowY + i);
        std::cout << "│" << '\n';
    }
    //right side
    for (int i = 1; i < windowHeight - 1; i++) {
        setCursorPosition(windowX + windowWidth - 1, windowY + i);
        std::cout << "│" << '\n';
    }
    //bottom└┘
    setCursorPosition(windowX, windowY + windowHeight - 1);
    std::cout << "└";
    for (int i = 0; i < windowWidth -2; i++) std::cout << "─";
    std::cout << "┘" << '\n';
    //top┌┐
    setCursorPosition(windowX, windowY);
    std::cout << "┌";
    for (int i = 0; i < windowWidth -2; i++) std::cout << "─";
    std::cout << "┐" <<'\n'; 
    
    
    //draw the text onto the window
    setColor("lightgrey");
    std::cout << "\033[30m";
    std::vector<std::string> message;
    int maxLineLength = windowWidth - 4;

    for (const std::string& line : text) {
        std::string part2 = line;
        while (static_cast<int>(part2.size()) > maxLineLength) {
            std::string part1 = part2.substr(0, maxLineLength);
            part2 = part2.substr(maxLineLength);
            message.push_back(part1);
        }
        message.push_back(part2); // Add remaining part
    }

    // Drawing text onto the board
    int offset = (windowHeight / 2 - 2 - message.size());
    for (int i = 0; i < static_cast<int>(message.size()); ++i) {
        // Ensure proper centering and positioning
        int x = windowX + 1 + ((windowWidth - message[i].size()) / 2);
        int y = windowY + 1 + i + offset;
        setCursorPosition(x, y);
        std::cout << message[i] << '\n';
    }
    
    //draw some fancy things at the bottom
    std::string strA1 = std::to_string(a1+1);
    std::string strA2 = std::to_string(a2);
    std::string thing = strA1 + "/" + strA2 + " :  [Cancel]   [ Okay ]";
    setCursorPosition(windowX + windowWidth - thing.size() - 1, windowY + windowHeight - 2);
    std::cout << thing << '\n';
    
    
    
    
    setCursorPosition(0, rows);
    std::cout << "\033[0m";
}

