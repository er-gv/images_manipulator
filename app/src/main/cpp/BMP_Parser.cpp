//
// Created by nate on 5/23/19.
//

#include "BMP_Parser.h"
#include "int_types.h"

static BMP_Parser* openFile(const char* path){
     std::ifstream is (path, std::ifstream::binary);
     if(is)
        return new BMP_Parser(is);
     else return NULL;
}

BMP_Parser::BMP_Parser(std::ifstream& ifs):m_is(ifs){
    sizeofile = m_is.tellg();
}

int BMP_Parser::readHeader(){
    return 0;
}

int BMP_Parser::readInfoBlock(){
    return 0;

}

int BMP_Parser::readPallets(){
    return 0;
}

int BMP_Parser::readImageData(){
    return 0;
}
