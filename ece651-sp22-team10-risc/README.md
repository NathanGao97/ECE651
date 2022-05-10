ece651-sp22-team10-RISC ![pipeline](https://gitlab.oit.duke.edu/zj65/ece651-sp22-team10-risc/badges/main/pipeline.svg) ![coverage](https://gitlab.oit.duke.edu/zj65/ece651-sp22-team10-risc/badges/main/coverage.svg?job=test) 
====================================== 

ECE651 Team Project
## Coverage
[Detailed coverage](https://zj65.pages.oit.duke.edu/ece651-sp22-team10-risc/dashboard.html)


## UML
you can use the following uml to access more clear uml
https://lucid.app/lucidchart/9a51ec8d-d820-410e-8ef7-cbe04596f1ba/edit?invitationId=inv_0e49f173-cffc-47d6-a692-b941b942409f&page=th3V9BFk6-cq#


# how to transport jpg/png image to SVG then to fxml 

## transport from image to svg
        
        You can use the following website to transform image to svg
        https://convertio.co/zh/png-svg/
        https://www.img2go.com/resize-image

        Then use the following website to resize the svg:
        https://www.iloveimg.com/resize-image/resize-svg

## transform from svg to fxml
        
        #open the converted svg file copy the path concent in d
        #eg. <path d="M 2 3 C 2 4.597656 1.601562 5.996094 1 5.996094 Z"> </path>

        str = "svg path"  # like "M 2 3 C 2 4.597656 1.601562 5.996094 1 5.996094 Z"       
        str_arr = str.split()

        i = 0
        
        while i < len(str_arr) :
        if str_arr[i] == "C":
        x1 = float(str_arr[i+1])
        y1 = float(str_arr[i+2])
        x2 = float(str_arr[i+3])
        y2 = float(str_arr[i+4])
        x3 = float(str_arr[i+5])
        y3 = float(str_arr[i+6])
        print("<CubicCurveTo controlX1=\"{}\" controlY1=\"{}\" controlX2=\"{}\" controlY2=\"{}\" x=\"{}\" y=\"{}\" />".format(x1, y1, x2, y2, x3, y3))
        i = i+7;
        elif str_arr[i] == "M":
        x1 = float(str_arr[i+1])
        y1 = float(str_arr[i+2])
        print("<MoveTo x=\"{}\" y=\"{}\"/>".format(x1, y1))
        i = i+3
        elif str_arr[i] == "L":
        x1 = float(str_arr[i+1])
        y1 = float(str_arr[i+2])
        print("<LineTo x=\"{}\" y=\"{}\"/>".format(x1, y1))
        i = i+3
        elif str_arr[i] == "Z":
        print("<ClosePath />")
        i = i+1
        else:
        print(str_arr[i])
        i = i+1