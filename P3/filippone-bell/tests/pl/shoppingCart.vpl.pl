
dbase(vpl,[violetMiddleLabels,violetAssociation,violetInterface,violetClass]).

table(violetClass,[id,"name","fields","methods",x,y]).
violetClass(classnode0,'ShoppingCart','subTotalMoney;vatAmount;totalMoney','',224,229).
violetClass(classnode1,'Customer','customerName;billingAddress;shippingAddress;emailaddress;otherDetails','',567,214).
violetClass(classnode2,'CreditCard','issuer;cardNumber;dateOfExpiry','',384,403).
violetClass(classnode3,'PreferredCustomer','discountRate;approvalDate','',562,413).
violetClass(classnode4,'ItemToPurchase','quantity;pricePerUnit','',227,591).
violetClass(classnode5,'Product','productName;productDescription;otehrDetails','',558,586).

table(violetInterface,[id,"name","methods",x,y]).

table(violetAssociation,[id,"role1","arrow1",type1,"role2","arrow2",type2,"bentStyle","lineStyle",cid1,cid2]).
violetAssociation(id0,'*','',classnode,'1','',classnode,'HVH','',classnode4,classnode5).
violetAssociation(id1,'1','',classnode,'1..*','',classnode,'HVH','',classnode0,classnode4).
violetAssociation(id2,'1','',classnode,'1','',classnode,'','',classnode0,classnode2).
violetAssociation(id3,'*','',classnode,'1','',classnode,'HVH','',classnode2,classnode3).
violetAssociation(id4,'*','',classnode,'1','',classnode,'HVH','',classnode0,classnode1).
violetAssociation(id5,'','',classnode,'','TRIANGLE',classnode,'VHV','',classnode3,classnode1).

table(violetMiddleLabels,[id,cid1,cid2,"label"]).
