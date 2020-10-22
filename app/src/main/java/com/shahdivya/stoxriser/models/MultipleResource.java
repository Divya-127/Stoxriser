        package com.shahdivya.stoxriser.models;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

        public class MultipleResource
        {
                @SerializedName("C_ID")
                @Expose
                private Integer cID;
                @SerializedName("C_NAME")
                @Expose
                private String cNAME;
                @SerializedName("NO_OF_SHARES_AVAIL")
                @Expose
                private Integer nOOFSHARESAVAIL;
                @SerializedName("TOTAL_NO_OF_SHARES")
                @Expose
                private Integer tOTALNOOFSHARES;

                public MultipleResource() {
                }
        //        public MultipleResource(Long cID, String cNAME, Long nOOFSHARESAVAIL, Long tOTALNOOFSHARES) {
        //                super();
        //                this.cID = cID;
        //                this.cNAME = cNAME;
        //                this.nOOFSHARESAVAIL = nOOFSHARESAVAIL;
        //                this.tOTALNOOFSHARES = tOTALNOOFSHARES;
        //        }

                public Integer getCID() {
                        return cID;
                }

                public void setCID(Integer cID) {
                        this.cID = cID;
                }

                public String getCNAME() {
                        return cNAME;
                }

                public void setCNAME(String cNAME) {
                        this.cNAME = cNAME;
                }

                public Integer getNOOFSHARESAVAIL() {
                        return nOOFSHARESAVAIL;
                }

                public void setNOOFSHARESAVAIL(Integer nOOFSHARESAVAIL) {
                        this.nOOFSHARESAVAIL = nOOFSHARESAVAIL;
                }

                public Integer getTOTALNOOFSHARES() {
                        return tOTALNOOFSHARES;
                }

                public void setTOTALNOOFSHARES(Integer tOTALNOOFSHARES) {
                        this.tOTALNOOFSHARES = tOTALNOOFSHARES;
                }
        }