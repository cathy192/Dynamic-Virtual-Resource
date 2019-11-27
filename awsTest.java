import com.amazonaws.services.ec2.model.DescribeImagesRequest;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory;
import com.amazonaws.AmazonClientException;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.Reservation;
import java.util.*;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.ec2.model.CreateSecurityGroupRequest;
import com.amazonaws.services.ec2.model.CreateSecurityGroupResult;
import com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressResult;
import com.amazonaws.services.ec2.model.IpPermission;
import com.amazonaws.services.ec2.model.IpRange;
import com.amazonaws.services.ec2.model.SecurityGroup;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.DryRunResult;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import java.lang.NullPointerException;
import java.lang.*;
import com.amazonaws.services.ec2.model.DryRunSupportedRequest;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.InstanceType;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.CreateTagsResult;
import com.amazonaws.services.ec2.model.DescribeRegionsResult;
import com.amazonaws.services.ec2.model.Region;
import com.amazonaws.services.ec2.model.AvailabilityZone;
import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesResult;
import com.amazonaws.services.ec2.model.DescribeRegionsResult;
import com.amazonaws.services.ec2.model.Region;
import com.amazonaws.services.ec2.model.RebootInstancesRequest;
import com.amazonaws.services.ec2.model.Image;
import com.amazonaws.services.ec2.model.RebootInstancesResult;
import com.amazonaws.services.ec2.model.DescribeImagesResult;
import com.amazonaws.services.ec2.model.DescribeImageAttributeRequest;
import com.amazonaws.services.ec2.model.DescribeImagesResult;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.MonitorInstancesRequest;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.AllocateAddressRequest;
import com.amazonaws.services.ec2.model.AllocateAddressResult;
import com.amazonaws.services.ec2.model.AssociateAddressRequest;
import com.amazonaws.services.ec2.model.DescribeKeyPairsResult;
import com.amazonaws.services.ec2.model.KeyPairInfo;
import com.amazonaws.services.ec2.model.AssociateAddressResult;
import com.amazonaws.services.ec2.model.DeleteKeyPairRequest;
import com.amazonaws.services.ec2.model.DeleteKeyPairResult;
import com.amazonaws.services.ec2.model.CreateKeyPairRequest;
import com.amazonaws.services.ec2.model.CreateKeyPairResult;
import com.amazonaws.services.ec2.model.DomainType;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsRequest;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsResult;
import com.amazonaws.services.ec2.model.CreateSecurityGroupRequest;
import com.amazonaws.services.ec2.model.CreateSecurityGroupResult;
import com.amazonaws.services.ec2.model.DeleteSecurityGroupRequest;
import com.amazonaws.services.ec2.model.DeleteSecurityGroupResult;
import com.amazonaws.services.ec2.model.ReleaseAddressRequest;
import com.amazonaws.services.ec2.model.ReleaseAddressResult;
import com.amazonaws.services.ec2.model.Address;
import com.amazonaws.services.ec2.model.DescribeAddressesResult;
import com.amazonaws.services.ec2.model.UnmonitorInstancesRequest;
public class awsTest {
	/*
	 * Cloud Computing, Data Computing Laboratory
	 * Department of Computer Science
	 * Chungbuk National University
	 */
	static AmazonEC2 ec2;
	private static void init() throws Exception {
		/*
		 * The ProfileCredentialsProvider will return your [default]
		 * credential profile by reading from the credentials file located at
		 * (~/.aws/credentials).
		 */
		ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
		try {
			credentialsProvider.getCredentials();
		} catch (Exception e) {
			throw new AmazonClientException(
					"Cannot load the credentials from the credential profiles file. " +
					"Please make sure that your credentials file is at the correct " +
					"location (~/.aws/credentials), and is in valid format.",
					e);

		}
		ec2 = AmazonEC2ClientBuilder.standard()
			.withCredentials(credentialsProvider)
			.withRegion("us-east-2") /* check the region at AWS console */
			.build();
	}

	public static void main(String[] args) throws Exception {
		init();
		Scanner menu = new Scanner(System.in);
		Scanner id_string = new Scanner(System.in);
		int number = 0;
		boolean loop=true;
		String id="0";
		while(loop)
		{
			System.out.println(" ");
			System.out.println(" ");
			System.out.println("------------------------------------------------------------");
			System.out.println(" Amazon AWS Control Panel using SDK ");
			System.out.println(" ");
			System.out.println(" Cloud Computing, Computer Science Department ");
			System.out.println(" at Chungbuk National University ");
			System.out.println("------------------------------------------------------------");
			System.out.println(" 1. list instance 2. available zones ");
			System.out.println(" 3. start instance 4. available regions ");
			System.out.println(" 5. stop instance 6. create instance ");
			System.out.println(" 7. reboot instance 8. list images ");
		        System.out.println(" 9. monitoring instance 10. unmonitoring instance ");
                        System.out.println(" 11. Associate Address   12. Describe Address");
                        System.out.println(" 13. Release Address  14. createKeyPair");
                        System.out.println(" 15. createKeyPair 16. deleteKeyPair");
                        System.out.println(" 17. createSecurity Group 18. Describe security group ");
                        System.out.println(" 19. delete Security group 20. Authorize Security Gruop ");


			System.out.println(" 99. quit ");
			System.out.println("------------------------------------------------------------");
			System.out.print("Enter an integer: ");
			number=menu.nextInt();
			switch(number) {
				case 1:

					listInstances();
					break;

				case 2:
					listZones();
					break;
				case 3:

					System.out.println("Enter the instance id: ");
					id= id_string.nextLine();
					startInstance(id);
					break;

				case 4:
					listRegions();
					break;

				case 5:

					System.out.println("Enter the instance id: ");
					id= id_string.nextLine();
					stopInstance(id);
					break;

				case 6:
					System.out.print("Enter the ami id: ");
					createInstance();
					break;
				
				case 7:
					System.out.print("Enter the instance id:");
					id=id_string.nextLine();
					rebootInstance(id);
					break;
				case 8:
					listImage();
					break;

				case 9:
					System.out.print("Enter the instance id:");
                                        id=id_string.nextLine();
					monitorInstance(id);
					break;
				case 10:
					System.out.print("Enter the instance id:");
                                        id=id_string.nextLine();
                                        unmonitorInstance(id);
                                        break;

				case 11:
					System.out.print("Enter the instance id:");
                                        id=id_string.nextLine();
					allocateAddress(id);
					break;
				case 12:
					describeAddress();
					break;
		
				case 13:
					System.out.print("Enter the address id:");
                                        id=id_string.nextLine();
					releaseAddress(id);
					break;

				case 14:
					System.out.print("Enter the key name you want to create:");
                                        id=id_string.nextLine();
					createKeyPair(id);
					break;
				case 15:
					describeKeyPair();
					break;
				case 16:
					System.out.print("Enter the key name want to delete:");
                                        id=id_string.nextLine();
					deleteKeyPair(id);
					break;
				case 17:
					String group_name="", group_desc="", vpc_id="";
					System.out.print("Enter the group name : ");
                                        group_name=id_string.nextLine();
					System.out.println("");
					System.out.print("Type the description of the group : ");
                                        group_desc=id_string.nextLine();
					System.out.println("");
					System.out.print("Enter the vpc id : ");
                                        vpc_id=id_string.nextLine();					
					CreateSecurityGroup(group_name, group_desc, vpc_id);
					break;
				case 18:
					System.out.print("Enter the group id want to know:");
                                        id=id_string.nextLine();
					DescribrSecurityGroup(id);
					break;
				case 19:
					System.out.print("Enter the group id you want to delete:");
                                        id=id_string.nextLine();
					DeleteSecurityGroup(id);
					break;
				case 20:
				//	AuthrizeSecurityGroup();
					break;	
				case 99:
					System.out.println("end the Amazon AWS Control");
					loop=false;
					break;

				default:

					System.out.println("Please enter right number of menu");
					break;
			}
		}
	}
	public static void listInstances(){
		System.out.println("");
		System.out.println("Listing instances....");
		boolean done = false;
		DescribeInstancesRequest request = new DescribeInstancesRequest();
		while(!done) {
			DescribeInstancesResult response = ec2.describeInstances(request);
			for(Reservation reservation : response.getReservations()) {
				for(Instance instance : reservation.getInstances()) {
					System.out.printf(
							"[id] %s, " +
							"[AMI] %s, " +
							"[type] %s, " +
							"[state] %10s, " +
							"[monitoring state] %s",
							instance.getInstanceId(),
							instance.getImageId(),
							instance.getInstanceType(),
							instance.getState().getName(),
							instance.getMonitoring().getState());
				}
				System.out.println();
			}
			request.setNextToken(response.getNextToken());
			if(response.getNextToken() == null) {
				done = true;
			}
		}
	}

	public static void startInstance(String instance_name){ //start instance
		final AmazonEC2 new_ec2= AmazonEC2ClientBuilder.defaultClient();


		System.out.printf(
				"Starting .... %s", instance_name);

		DryRunSupportedRequest<StartInstancesRequest> dry_request =
			() -> {
				StartInstancesRequest request = new StartInstancesRequest()
					.withInstanceIds(instance_name);

				return request.getDryRunRequest();
			};
		// the original request of the dry run
		DryRunResult dry_response = ec2.dryRun(dry_request);

		if(!dry_response.isSuccessful()) {// if dry-run was not succeessful
			System.out.printf(
					"Failed dry run to start instance %s", instance_name);

			throw dry_response.getDryRunResponse(); 
		}//        //start instance that user put id 

		StartInstancesRequest request = new StartInstancesRequest()
			.withInstanceIds(instance_name);

		ec2.startInstances(request);

		System.out.printf("Successfully started instance %s", instance_name);
	}	

	public static void stopInstance(String instance_name){//stop instance

		final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

		DryRunSupportedRequest<StopInstancesRequest> dry_request =
			() -> {
				StopInstancesRequest request = new StopInstancesRequest()
					.withInstanceIds(instance_name);

				return request.getDryRunRequest();
			};

		DryRunResult dry_response = ec2.dryRun(dry_request);

		if(!dry_response.isSuccessful()) {
			System.out.printf(
					"Failed dry run to stop instance %s", instance_name);
			throw dry_response.getDryRunResponse();
		}

		StopInstancesRequest request = new StopInstancesRequest()
			.withInstanceIds(instance_name);

		ec2.stopInstances(request);

		System.out.printf("Successfully stop instance %s", instance_name);


	}

	public static void createInstance(){ //create instance;
			Scanner id_string = new Scanner(System.in);
			String ami_id;
			String reservation_id="0";
			ami_id= id_string.nextLine();
			final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

			RunInstancesRequest run_request = new RunInstancesRequest()
			.withImageId(ami_id)
			.withInstanceType(InstanceType.T2Micro)
			.withMaxCount(1)
			.withMinCount(1);

			RunInstancesResult run_response = ec2.runInstances(run_request);

			reservation_id = run_response.getReservation().getInstances().get(0).getInstanceId();

		//	throw new AmazonClientException(
		//	"fail to create instance",e);
		//	System.out.println("error");


		// RunInstancesResult run_response = ec2.runInstances(run_request);
		//
		//       reservation_id = run_response.getReservation().getInstances().get(0).getInstanceId();

		System.out.printf(
		"Successfully started EC2 instance %s based on AMI %s", reservation_id, ami_id);

		 	}

	public static void listZones(){
		final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

		System.out.println("Available zones..");
		DescribeAvailabilityZonesResult zones_response =
			ec2.describeAvailabilityZones();

		for(AvailabilityZone zone : zones_response.getAvailabilityZones()) {
			System.out.printf(
					"[id] 	%s ,"+
					"[region]	%s, "+
					"[zone]	%s",
					zone.getZoneId(),
					zone.getRegionName(),
					zone.getZoneName());
			System.out.println();    
		}
	}


	public static void listRegions(){

		final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
		DescribeRegionsResult regions_response = ec2.describeRegions();
		System.out.println("Available regions ....");
		for(Region region : regions_response.getRegions()) {
			System.out.printf(
					"[region] %15s,	" +
					"[endpoint] %10s",
					region.getRegionName(),
					region.getEndpoint());
			System.out.println();

		}

	}

	public static void rebootInstance(String instance_id){

	  final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

        RebootInstancesRequest request = new RebootInstancesRequest()
            .withInstanceIds(instance_id);

        RebootInstancesResult response = ec2.rebootInstances(request);

        System.out.printf(
            "Successfully rebooted instance %s", instance_id);


	}


	public static void listImage(){

	System.out.println("Listing Images.....");
	DescribeImagesRequest request=new DescribeImagesRequest(); 
//	final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
	request.withOwners("self");
 	DescribeImagesResult image_list=ec2.describeImages(request);
	for(Image image : image_list.getImages()){
			System.out.printf(
				"[ImageID]  %s,	"+
				"[Name]  %s,	"+
				"[Owner]  %s,	",
				image.getImageId(),
				image.getName(),
				image.getOwnerId());
			System.out.println();
			}
			}
	
      
	
	public static void monitorInstance(String instance_id){

	DryRunSupportedRequest<MonitorInstancesRequest> dry_request =
            () -> {
            MonitorInstancesRequest request = new MonitorInstancesRequest()
                .withInstanceIds(instance_id);

            return request.getDryRunRequest();
        };

        DryRunResult dry_response = ec2.dryRun(dry_request);

        if (!dry_response.isSuccessful()) {
            System.out.printf(
                "Failed dry run to enable monitoring on instance %s",
                instance_id);

            throw dry_response.getDryRunResponse();
        }

        MonitorInstancesRequest request = new MonitorInstancesRequest()
                .withInstanceIds(instance_id);

        ec2.monitorInstances(request);

        System.out.printf(
            "Successfully enabled monitoring for instance %s",
            instance_id);
    }


	public static void unmonitorInstance(String instance_id){


	   DryRunSupportedRequest<UnmonitorInstancesRequest> dry_request =
            () -> {
            UnmonitorInstancesRequest request = new UnmonitorInstancesRequest()
                .withInstanceIds(instance_id);

            return request.getDryRunRequest();
        };

        DryRunResult dry_response = ec2.dryRun(dry_request);

        if (!dry_response.isSuccessful()) {
            System.out.printf(
                "Failed dry run to disable monitoring on instance %s",
                instance_id);

            throw dry_response.getDryRunResponse();
        }

        UnmonitorInstancesRequest request = new UnmonitorInstancesRequest()
            .withInstanceIds(instance_id);

        ec2.unmonitorInstances(request);

        System.out.printf(
            "Successfully disabled monitoring for instance %s",
            instance_id);

		}


	public static void allocateAddress(String instance_id){

  	AllocateAddressRequest allocate_request = new AllocateAddressRequest()
            .withDomain(DomainType.Vpc);

        AllocateAddressResult allocate_response =
            ec2.allocateAddress(allocate_request);

        String allocation_id = allocate_response.getAllocationId();

        AssociateAddressRequest associate_request =
            new AssociateAddressRequest()
                .withInstanceId(instance_id)
                .withAllocationId(allocation_id);

        AssociateAddressResult associate_response =
            ec2.associateAddress(associate_request);

        System.out.printf(
            "Successfully associated Elastic IP address %s " +
            "with instance %s",
            associate_response.getAssociationId(),
            instance_id);
    }			


	public static void describeAddress(){
	
	DescribeAddressesResult response = ec2.describeAddresses();
	for(Address address : response.getAddresses()) {
 	   System.out.printf(
            "[public IP]: %s,	" +
            "[domain]: %s	, " +
            "[allocation id]: %s,	" +
            "[NIC id]: %s",
            address.getPublicIp(),
            address.getDomain(),
            address.getAllocationId(),
            address.getNetworkInterfaceId());
	    }


	}	

	public static void releaseAddress(String alloc_id){

	ReleaseAddressRequest request = new ReleaseAddressRequest()
            .withAllocationId(alloc_id);

        ReleaseAddressResult response = ec2.releaseAddress(request);

        System.out.printf(
            "Successfully released elastic IP address %s", alloc_id);
    }


	public static void createKeyPair(String key_name){

	
         CreateKeyPairRequest request = new CreateKeyPairRequest()
            .withKeyName(key_name);

        CreateKeyPairResult response = ec2.createKeyPair(request);
	System.out.println("");
        System.out.printf(
            "Successfully created key pair named %s",
            key_name);
    }
	

	public static void describeKeyPair(){

	System.out.println("key list...");
	DescribeKeyPairsResult response = ec2.describeKeyPairs();

        for(KeyPairInfo key_pair : response.getKeyPairs()) {
            System.out.printf(
                "[name]:  %15s,	" +
                "[fingerprint]: %s",
                key_pair.getKeyName(),
                key_pair.getKeyFingerprint());
        	System.out.println();
	}
    }
	public static void deleteKeyPair(String key_name){

	 DeleteKeyPairRequest request = new DeleteKeyPairRequest()
            .withKeyName(key_name);

        DeleteKeyPairResult response = ec2.deleteKeyPair(request);

        System.out.printf(
            "Successfully deleted key pair named %s", key_name);
    }

	public static void CreateSecurityGroup(String group_name, String group_desc, String vpc_id){



        CreateSecurityGroupRequest create_request = new
            CreateSecurityGroupRequest()
                .withGroupName(group_name)
                .withDescription(group_desc)
                .withVpcId(vpc_id);

        CreateSecurityGroupResult create_response =
            ec2.createSecurityGroup(create_request);

        System.out.printf(
            "Successfully created security group named %s",
            group_name);

        IpRange ip_range = new IpRange()
            .withCidrIp("0.0.0.0/0");

        IpPermission ip_perm = new IpPermission()
            .withIpProtocol("tcp")
            .withToPort(80)
            .withFromPort(80)
            .withIpv4Ranges(ip_range);

        IpPermission ip_perm2 = new IpPermission()
            .withIpProtocol("tcp")
            .withToPort(22)
            .withFromPort(22)
            .withIpv4Ranges(ip_range);

        AuthorizeSecurityGroupIngressRequest auth_request = new
            AuthorizeSecurityGroupIngressRequest()
                .withGroupName(group_name)
                .withIpPermissions(ip_perm, ip_perm2);

        AuthorizeSecurityGroupIngressResult auth_response =
            ec2.authorizeSecurityGroupIngress(auth_request);

        System.out.printf(
            "Successfully added ingress policy to security group %s",
            group_name);
    }
		
	
	
	public static void DescribrSecurityGroup(String group_id){

	DescribeSecurityGroupsRequest request =
            new DescribeSecurityGroupsRequest()
                .withGroupIds(group_id);

        DescribeSecurityGroupsResult response =
            ec2.describeSecurityGroups(request);

        for(SecurityGroup group : response.getSecurityGroups()) {
            System.out.printf(
                "[id]:  %s, " +
                "[vpc id]: %s " +
                "[description]: %s",
                group.getGroupId(),
                group.getVpcId(),
                group.getDescription());
        	}
    
	}
	public static void DeleteSecurityGroup(String group_id){

	 DeleteSecurityGroupRequest request = new DeleteSecurityGroupRequest()
            .withGroupId(group_id);

        DeleteSecurityGroupResult response = ec2.deleteSecurityGroup(request);

        System.out.printf(
            "Successfully deleted security group with id %s", group_id);
   	 }


	
	
	
			
}



