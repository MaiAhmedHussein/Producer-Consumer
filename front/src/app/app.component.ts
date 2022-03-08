import { Component } from '@angular/core';
import { of, Observable } from "rxjs";
import { OnInit } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'simulator';
  constructor(private http: HttpClient) { }
  ngOnInit(): void {
    this.svg = document.getElementById("svg");////


  }
  reponse: any
  x: any
  y: any
  idOfMachine: any = 0;
  idOfTextMachine: any = 0;
  newLocal: any
  svg: any = []
  selected: any
  mai = "boba"
  m: any
  flag: any = true
  public firstSelectted: any = ""
  public secondSelected: any = ""
  idOflines: any = 0
  firstX: any
  firstY: any
  secondX: any
  secondY: any
  myfirstObject: any
  mysecondObject: any
  updatedcircle: any
  updatedrectangle: any
  text: any
  idSelected: any
  buttonName: any = "";
  today: any;
  from: any;
  idOfQueue: any = 0
  idOfTextQueue: any = 0
  num: any

  ///To get the dimentions when clicking on the screen
  mousePosition(event: any) {
    this.x = event.offsetX;
    this.y = event.offsetY;
    console.log("points is  " + this.x + "  " + this.y);
  }

  AddMachine() {

    if (this.x !== 0 && this.y !== 0) {
      const circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
      circle.setAttribute("cx", this.x);
      circle.setAttribute("cy", this.y);
      circle.setAttribute("r", "40");
      circle.setAttribute("fill", "#e6b2bc");
      //circle.addEventListener("click", this.getid);  //to select for connection 
      let id = "M" + this.idOfMachine;
      circle.setAttribute("id", id);
      this.svg.appendChild(circle);
      console.log(circle);
      // server.sendM(circle);
      let idtext = document.createElementNS("http://www.w3.org/2000/svg", "text");

      this.newLocal = this.x - 9;
      idtext.setAttribute("x", this.newLocal);
      idtext.setAttribute("y", this.y + 6);
      //  idtext.addEventListener("click", this.whichObject);
      let idtxt = "id" + this.idOfTextMachine;
      idtext.setAttribute("id", idtxt);
      this.idOfTextMachine++;
      let idText = document.createTextNode("M" + this.idOfTextMachine);
      idtext.appendChild(idText);
      this.svg.appendChild(idtext);
      this.idOfMachine++;

      this.x = 0;
      this.y = 0;
      this.sendM(id);
    }
  }

 
  AddQueue() {

    if (this.x !== 0 && this.y !== 0) {
      
      const rec = document.createElementNS("http://www.w3.org/2000/svg", "rect");
      rec.setAttribute("x", this.x);
      rec.setAttribute("y", this.y);
      rec.setAttribute("width", "70");
      rec.setAttribute("height", "60");
      rec.setAttribute("fill", "#b0d7eb");

      let id = "Q" + this.idOfQueue;
      rec.setAttribute("id", id);

      // rec.addEventListener("click", this.getid);
      ////////////////////////////////set name of products/////////
     
      //set number of products///
      let text = document.createElementNS("http://www.w3.org/2000/svg", "text");
      text.setAttribute("x", this.x + 33);
      text.setAttribute("y", this.y +40);
      let productTxt = "t" + this.idOfTextQueue;
      text.setAttribute("id", productTxt);
      let txt = document.createTextNode("0");
      text.appendChild(txt);
      ///add id of queue
      let idtext = document.createElementNS("http://www.w3.org/2000/svg", "text");
      idtext.setAttribute("x", this.x + 28);
      idtext.setAttribute("y", this.y + 20);
      let idtxt = "idd" + this.idOfTextQueue;
      idtext.setAttribute("id", idtxt);
      this.idOfTextQueue++;
      let idText = document.createTextNode("Q" + this.idOfQueue);
      idtext.appendChild(idText);
      ///////////////////////////
   
      this.svg.appendChild(rec);
      this.svg.appendChild(idtext);
      this.svg.appendChild(text);
      this.idOfQueue++;
      console.log(rec);
      // server.sendQ(rec);
      console.log("id of queue" + " " + rec.id);
      this.x = 0;
      this.y = 0;
      this.sendQ(id);
    }
  }



  getid(event: any) {
    if (this.flag === true) {
      this.firstSelectted = event.target.id
      if (this.firstSelectted === "svg") {
        this.flag = true;
      } else {
        //this.delete(this.firstSelectted[1]);
        //document.getElementById(this.firstSelectted)!.remove();
        this.flag = false;
      }

      console.log("firstObject:  " + this.firstSelectted);
    } else {
      this.secondSelected = event.target.id;
      if (this.firstSelectted === "svg") {
        this.flag = false;
      } else {
        //document.getElementById(this.secondSelected)!.remove();
        this.flag = true;
      }
      console.log("secondObject:  " + this.secondSelected);
    }
  }





  Addconnect() {
    console.log("first:   " + this.firstSelectted)
    if (this.firstSelectted !== "" && this.secondSelected !== "") {
      console.log("ohhhhhhhhhhhh")
      if (this.firstSelectted.charAt(0) === this.secondSelected.charAt(0)) {
        alert("you can not connect two machines or two queues ");
      } else if (this.firstSelectted.charAt(0) === "Q") {
        console.log("sssssssssss")
        this.myfirstObject = document.getElementById(this.firstSelectted);
        this.firstX = this.myfirstObject.getAttribute("x");
        this.firstY = this.myfirstObject.getAttribute("y");
        this.firstY = Number.parseInt(this.firstY) + 30;
        this.mysecondObject = document.getElementById(this.secondSelected);
        this.secondX = this.mysecondObject.getAttribute("cx");
        this.secondY = this.mysecondObject.getAttribute("cy");
        this.secondX = Number.parseInt(this.secondX) + 40;
        if (Number.parseInt(this.secondX) > Number.parseInt(this.firstX)) {
          this.firstX = Number.parseInt(this.firstX) + 70;
          this.secondX = Number.parseInt(this.secondX) - 80;
        }
        this.connection();
      }
      else if (this.firstSelectted.charAt(0) === "M") {
        console.log("pppppppppppp")
        this.myfirstObject = document.getElementById(this.firstSelectted);
        this.firstX = this.myfirstObject.getAttribute("cx");
        this.firstY = this.myfirstObject.getAttribute("cy");
        this.firstX = Number.parseInt(this.firstX) + 40;
        this.mysecondObject = document.getElementById(this.secondSelected);
        this.secondX = this.mysecondObject.getAttribute("x");
        this.secondY = this.mysecondObject.getAttribute("y");
        this.secondY = Number.parseInt(this.secondY) + 30;
        if (Number.parseInt(this.firstX) > Number.parseInt(this.secondX)) {
          this.secondX = Number.parseInt(this.secondX) + 70;
          this.firstX = Number.parseInt(this.firstX) - 80;
        }
        this.connection();
      }

    } else {
      alert("please select machine and queue to be connected.. ")
    }
    this.firstSelectted = "";
    this.secondSelected = "";

  }
  connection() {
    this.svg = document.getElementById("svg");
    const line = document.createElementNS("http://www.w3.org/2000/svg", "line");
    line.setAttribute("x1", this.firstX);
    line.setAttribute("y1", this.firstY);
    line.setAttribute("x2", this.secondX);
    line.setAttribute("y2", this.secondY);
    line.setAttribute("stroke", "black");
    line.setAttribute("stroke-width", "3");
    let id = "l" + this.idOflines;
    line.setAttribute("id", id);
    this.idOflines++;
    line.setAttribute("marker-end", "url(#arrowhead)");
    this.svg.appendChild(line);
    console.log(line);
    this.sendConnection();
  }


  
  delete() {
    if (this.firstSelectted == "svg") {
      this.flag = true;
      return;
    }
    var i = this.firstSelectted.substring(1, this.firstSelectted.length);
    if (this.firstSelectted[0] == "Q") {
      document.getElementById("Q" + i)!.remove();
      document.getElementById("t" + i)!.remove();
      document.getElementById("idd" + i)!.remove();
    } else if (this.firstSelectted[0] == "M") {
      document.getElementById("M" + i)!.remove();
      document.getElementById("id" + i)!.remove();
    } else if (this.firstSelectted[0] == "l") {
      document.getElementById("l" + i)!.remove();

    } else {
      this.flag = true;
      return;
    }
    this.flag = true;
  }




  sendQ(id: any) {
    this.http.get('http://localhost:8080/sendQ', {
      responseType: 'text',
      params: {
        id: id.substring(1)
      },
      observe: "response"

    })
      .subscribe((response) => {
        this.m = response.body

        console.log(this.m);
      })

  }
  sendM(id: any) {
    this.http.get('http://localhost:8080/sendM', {
      responseType: 'text',
      params: {
        id: id.substring(1)
      },
      observe: "response"

    })
      .subscribe((response) => {
        this.m = response.body

        console.log(this.m);
      })
  }
  sendConnection() {
    this.http.get('http://localhost:8080/sendConnection', {
      responseType: 'text',
      params: {
        from: this.firstSelectted,
        to: this.secondSelected
      },
      observe: "response"

    })
      .subscribe((response) => {
        this.m = response.body

        console.log(this.m);
      })

  }

  updateMachines(id: any, color: any) {
    id = "M" + id;
    this.updatedcircle = document.getElementById(id);
    console.log(this.updatedcircle);
    this.updatedcircle.setAttribute("fill", color);

  }


  updateQueues(id: any, noOfProducts: any) {
    let idTxt = "t" + id;
    id = "Q" + id;
    console.log("idtext:    " + idTxt);
    this.text = document.getElementById(idTxt);
    this.text.textContent = noOfProducts;
    this.updatedrectangle = document.getElementById(id);
    //this.updatedrectangle.setAttribute("fill", "#800000");
    console.log(id + " M " + noOfProducts)
  }

  getNoOfProduct() {
    this.num = (<HTMLInputElement>document.getElementById("inputnumber")).value;
    console.log(this.num)
    this.http.get('http://localhost:8080/addProduct', {
      responseType: 'text',
      params: {
        numberOfProducts: this.num
      },
      observe: "response"

    })
      .subscribe((response) => {

      })
  }
  startsim() {
    this.http.get('http://localhost:8080/start', {
      responseType: 'text',
      params: {

      },
      observe: "response"

    })
      .subscribe((response) => {
        this.m = response.body

        console.log(this.m + "helloz");
      })
    this.updateAll();
  }

  updateAll() {
    //get response from backend
    this.http.get('http://localhost:8080/update', {
      responseType: 'json',
      params: {

      },
      observe: "response"

    })
      .subscribe((response) => {
        this.reponse = response.body
        console.log(this.reponse)


        if (this.reponse.qid === undefined) {
          console.log("in update undefined");
          this.updateAll();
        } else {
          if (this.reponse.mid != -2) {
            this.updateQueues(this.reponse.qid, this.reponse.noOfproducts);
            if (this.reponse.mid != -1) {
              console.log("d5lt");
              this.updateMachines(this.reponse.mid, this.reponse.fill);
            }

            this.updateAll();
            this.reponse = null;
          }
        }

      })
  }
  stop() {
    this.http.get('http://localhost:8080/stop', {
      responseType: 'text',
      params: {

      },
      observe: "response"

    })
      .subscribe((response) => {
        this.m = response.body

        console.log(this.m + "helloz");
      })

  }
  replay() {
    this.http.get('http://localhost:8080/replay', {
      responseType: 'text',
      params: {

      },
      observe: "response"

    })
      .subscribe((response) => {
        this.m = response.body

        console.log(this.m + "helloz");
      })
    this.updateAll();

  }
  ///sttart new simulation
  startnew() {
    location.reload();
    this.newSim();
  }
  newSim() {
    this.http.get('http://localhost:8080/newSim', {
      responseType: 'text',
      params: {

      },
      observe: "response"

    })
      .subscribe((response) => {
        this.m = response.body

        console.log(this.m + "helloooo Boba");
      })

  }

}