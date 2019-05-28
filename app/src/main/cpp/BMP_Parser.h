//
// Created by nate on 5/23/19.
//

#ifndef IMAGEMANIPULATOR_BMP_PARSER_H
#define IMAGEMANIPULATOR_BMP_PARSER_H

#include "int_types.h"
#include <fstream>


class BMP_Parser {

    private:
        long int sizeofile;
        u8* image_data;
        typedef struct {
           unsigned short int type;                 /* Magic identifier            */
           unsigned int size;                       /* File size in bytes          */
           unsigned short int reserved1, reserved2;
           unsigned int offset;                     /* Offset to image data, bytes */
        } HEADER;

        typedef struct {
           unsigned int size;               /* Header size in bytes      */
           int width,height;                /* Width and height of image */
           unsigned short int planes;       /* Number of colour planes   */
           unsigned short int bits;         /* Bits per pixel            */
           unsigned int compression;        /* Compression type          */
           unsigned int imagesize;          /* Image size in bytes       */
           int xresolution,yresolution;     /* Pixels per meter          */
           unsigned int ncolours;           /* Number of colours         */
           unsigned int importantcolours;   /* Important colours         */
        } INFOHEADER;

         std::ifstream& m_is;
    public:

        BMP_Parser(std::ifstream & );
        ~BMP_Parser();
        static BMP_Parser openFile(const char* path);
        bool canOpenFile();
        int readHeader();
        int readInfoBlock();
        int readPallets();
        int readImageData();
};



#endif //IMAGEMANIPULATOR_BMP_PARSER_H
